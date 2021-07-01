package crux.visualisation.domain

import crux.api.CruxDocument
import crux.api.CruxDocument.builder
import crux.api.ICruxAPI
import crux.api.tx.submitTx
import crux.visualisation.adapter.blocking
import crux.visualisation.adapter.withLog
import crux.visualisation.domain.input.InputData
import crux.visualisation.domain.input.InputData.*
import crux.visualisation.domain.input.InputData.Color.*
import crux.visualisation.domain.input.InputData.Link.DeleteLink
import crux.visualisation.domain.input.InputData.Link.PutLink
import crux.visualisation.domain.input.InputData.None.DeleteSimpleColor
import crux.visualisation.domain.input.InputDataWithTimes
import crux.visualisation.domain.input.Operation.DELETE
import crux.visualisation.domain.input.Operation.PUT
import crux.visualisation.domain.input.ValidTimeData.HasNoTimes
import crux.visualisation.domain.input.ValidTimeData.HasValidTime
import crux.visualisation.domain.input.ValidTimeData.HasValidTime.*
import crux.visualisation.domain.input.operation
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset.UTC
import java.util.*

class CruxAdapter(crux: ICruxAPI) {
    companion object {
        const val SIMPLE_ID = "doc"
        const val TYPE_KEY = "type"
        const val COLOUR_KEY = "colour"
        const val FROM_KEY = "from"
        const val TO_KEY = "to"

        const val TYPE_SIMPLE = "simple"
        const val TYPE_COLOUR = "colour"
        const val TYPE_LINK = "link"
    }

    private val crux = crux
        .blocking(Duration.ofSeconds(5))
        .withLog()

    private val date = LocalDate.now()

    fun submit(inputDataWithTimes: InputDataWithTimes): HistoryItem {
        crux.submitTx {
            inputDataWithTimes.run {
                validTimeData.run {
                    when (inputData.operation) {
                        PUT -> when (this) {
                            is HasNoTimes -> put(inputData.document)
                            is OnlyValidTime -> put(inputData.document from validTime())
                            is HasEndValidTime -> put(inputData.document from validTime() until endValidTime())
                        }
                        DELETE -> when (this) {
                            is HasNoTimes -> delete(inputData.id)
                            is OnlyValidTime -> delete(inputData.id from validTime())
                            is HasEndValidTime -> delete(inputData.id from validTime() until endValidTime())
                        }
                    }
                }
            }
        }

        val (transaction, transactionInstant) = crux.lastSubmitted
        val transactionTime = transactionInstant.time.toInstant().atZone(UTC).toLocalTime()
        return HistoryItem(
            inputDataWithTimes.inputData,
            inputDataWithTimes.validTimeData,
            transactionTime,
            transaction,
            transactionInstant
        )
    }

    operator fun get(validTime: LocalTime, transactionTime: LocalTime) = get(validTime.toDate(), transactionTime.toDate())
    operator fun get(validTime: Date, transactionTime: Date) =
        crux.db(validTime, transactionTime)
            .entity(SIMPLE_ID)
            ?.get(COLOUR_KEY) as String?

    private fun LocalTime.toDate() = LocalDateTime.of(date, this).atZone(UTC).toInstant().let(Date::from)

    private fun HasValidTime.validTime() = validTime.toDate()
    private fun HasEndValidTime.endValidTime() = endValidTime.toDate()

    private val InputData.document get(): CruxDocument =
        when (this) {
            DeleteSimpleColor, is DeleteColor, is DeleteLink -> throw IllegalStateException()

            is PutSimpleColor -> builder(id)
                .put(TYPE_KEY, TYPE_SIMPLE)
                .put(COLOUR_KEY, color.hex)
                .build()

            is PutColor -> builder(id)
                .put(TYPE_KEY, TYPE_COLOUR)
                .put(COLOUR_KEY, color.hex)
                .build()

            is PutLink -> builder(id)
                .put(TYPE_KEY, TYPE_LINK)
                .put(FROM_KEY, from.hex)
                .put(TO_KEY, to.hex)
                .build()
        }

    private val InputData.id get(): String =
        when (this) {
            DeleteSimpleColor, is PutSimpleColor -> SIMPLE_ID
            is Color -> color.hex
            is Link -> "${from.hex}-${to.hex}"
        }

}