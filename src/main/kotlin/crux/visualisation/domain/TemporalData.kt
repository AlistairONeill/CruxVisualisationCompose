package crux.visualisation.domain

import androidx.compose.runtime.MutableState
import crux.api.ICruxAPI
import crux.visualisation.domain.history.HistoryItem
import crux.visualisation.domain.input.InputDataWithTimes
import crux.visualisation.domain.visualisation.VisualisationMode
import crux.visualisation.domain.visualisation.VisualisationMode.SimpleColorGraph

class TemporalData private constructor(
    val cruxAdapter: CruxAdapter,
    val history: List<HistoryItem>,
    val visualisationMode: VisualisationMode,
    val selectedVisualisationColor: VisualisationColor?
) {
    companion object {
        fun new(crux: ICruxAPI): TemporalData = TemporalData(
            CruxAdapter(crux),
            emptyList(),
            SimpleColorGraph,
            null
        )
    }

    fun submit(inputWithTimes: InputDataWithTimes) =
        TemporalData(
            cruxAdapter,
            history + cruxAdapter.submit(inputWithTimes),
            visualisationMode,
            selectedVisualisationColor
        )

    fun withVisualisationMode(visualisationMode: VisualisationMode) =
        TemporalData(
            cruxAdapter,
            history,
            visualisationMode,
            selectedVisualisationColor
        )

    fun selectVisualisationColor(visualisationColor: VisualisationColor?) =
        TemporalData(
            cruxAdapter,
            history,
            visualisationMode,
            visualisationColor
        )
}

fun MutableState<TemporalData>.toInputAcceptor(): (InputDataWithTimes) -> Unit = { value = value.submit(it) }

fun MutableState<TemporalData>.toSetVisualisationColor(): (VisualisationColor?) -> Unit = { value = value.selectVisualisationColor(it) }