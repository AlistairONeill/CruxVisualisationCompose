package crux.visualisation.domain.visualisation

import androidx.compose.ui.graphics.Color
import crux.visualisation.domain.HistoryItem
import crux.visualisation.domain.TemporalData
import crux.visualisation.domain.hexToColor
import crux.visualisation.domain.input.ValidTimeData.HasNoTimes
import crux.visualisation.domain.input.ValidTimeData.HasValidTime.HasEndValidTime
import crux.visualisation.domain.input.ValidTimeData.HasValidTime.OnlyValidTime
import java.time.LocalTime

data class GraphRenderData(
    val validTimes: List<LocalTime>,
    val transactionTimes: List<LocalTime>,
    val data: List<List<Color?>>
)

fun TemporalData.getRenderData(): GraphRenderData {
    val validTimes = history.flatMap(HistoryItem::validTimes).sorted()
    val transactionTimes = history.map(HistoryItem::transactionTime).sorted()

    return GraphRenderData(
        validTimes,
        transactionTimes,
        validTimes.map { validTime ->
            transactionTimes.map { transactionTime ->
                cruxAdapter[validTime, transactionTime]?.hexToColor()?.let(::Color)
            }
        }
    )
}

private val HistoryItem.validTimes get() = validTimeData.run {
    when (this) {
        is HasEndValidTime -> listOf(validTime, endValidTime)
        is OnlyValidTime -> listOf(validTime)
        HasNoTimes -> listOf(transactionTime)
    }
}