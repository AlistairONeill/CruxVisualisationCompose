@file:Suppress("FunctionName")

package crux.visualisation.components.visualisation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import crux.visualisation.components.visualisation.graph.SimpleColorGraphPanel
import crux.visualisation.domain.TemporalData
import crux.visualisation.domain.visualisation.VisualisationMode.SimpleColorGraph
import crux.visualisation.exhaustive

@Composable
fun VisualisationPanel(
    mutableTemporalData: MutableState<TemporalData>
) {
    when(mutableTemporalData.value.visualisationMode) {
        SimpleColorGraph -> SimpleColorGraphPanel(mutableTemporalData)
    }.exhaustive
}