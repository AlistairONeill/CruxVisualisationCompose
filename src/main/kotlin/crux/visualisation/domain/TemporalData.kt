package crux.visualisation.domain

import crux.api.ICruxAPI
import crux.visualisation.domain.input.InputDataWithTimes
import java.util.*

class TemporalData private constructor(
    val cruxAdapter: CruxAdapter,
    val history: List<HistoryItem>
) {
    companion object {
        fun new(crux: ICruxAPI): TemporalData = TemporalData(
            CruxAdapter(crux),
            emptyList()
        )
    }

    operator fun get(validTime: Date, transactionTime: Date) = cruxAdapter[validTime, transactionTime]

    fun submit(inputWithTimes: InputDataWithTimes) =
        TemporalData(
            cruxAdapter,
            history + cruxAdapter.submit(inputWithTimes)
        )
}