package crux.visualisation.domain

import crux.api.ICruxAPI
import crux.visualisation.domain.history.HistoryItem
import crux.visualisation.domain.input.InputDataWithTimes
import crux.visualisation.domain.visualisation.VisualisationMode
import crux.visualisation.domain.visualisation.VisualisationMode.SimpleColorGraph
import java.util.*

class TemporalData private constructor(
    val cruxAdapter: CruxAdapter,
    val history: List<HistoryItem>,
    val visualisationMode: VisualisationMode
) {
    companion object {
        fun new(crux: ICruxAPI): TemporalData = TemporalData(
            CruxAdapter(crux),
            emptyList(),
            SimpleColorGraph
        )
    }

    operator fun get(validTime: Date, transactionTime: Date) = cruxAdapter[validTime, transactionTime]

    fun submit(inputWithTimes: InputDataWithTimes) =
        TemporalData(
            cruxAdapter,
            history + cruxAdapter.submit(inputWithTimes),
            visualisationMode
        )
}