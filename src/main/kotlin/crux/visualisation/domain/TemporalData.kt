package crux.visualisation.domain

import androidx.compose.runtime.MutableState
import crux.api.ICruxAPI
import crux.visualisation.domain.history.HistoryItem
import crux.visualisation.domain.input.InputDataWithTimes
import crux.visualisation.domain.visualisation.VisualisationData
import crux.visualisation.domain.visualisation.VisualisationData.SimpleColorGraphData

class TemporalData private constructor(
    val cruxAdapter: CruxAdapter,
    val history: List<HistoryItem>,
    val visualisationData: VisualisationData,
    val selectedVisualisationColor: VisualisationColor?
) {
    companion object {
        fun new(crux: ICruxAPI): TemporalData = TemporalData(
            CruxAdapter(crux),
            emptyList(),
            SimpleColorGraphData,
            null
        )
    }

    fun submit(inputWithTimes: InputDataWithTimes) =
        TemporalData(
            cruxAdapter,
            history + cruxAdapter.submit(inputWithTimes),
            visualisationData,
            selectedVisualisationColor
        )

    fun withVisualisationData(visualisationData: VisualisationData) =
        TemporalData(
            cruxAdapter,
            history,
            visualisationData,
            selectedVisualisationColor
        )

    fun selectVisualisationColor(visualisationColor: VisualisationColor?) =
        TemporalData(
            cruxAdapter,
            history,
            visualisationData,
            visualisationColor
        )
}

fun MutableState<TemporalData>.toInputAcceptor(): (InputDataWithTimes) -> Unit = { value = value.submit(it) }

fun MutableState<TemporalData>.toSetVisualisationColor(): (VisualisationColor?) -> Unit = { value = value.selectVisualisationColor(it) }