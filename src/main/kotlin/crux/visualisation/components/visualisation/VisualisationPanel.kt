@file:Suppress("FunctionName")

package crux.visualisation.components.visualisation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import crux.visualisation.components.visualisation.graph.SimpleColorGraphPanel
import crux.visualisation.domain.CruxAdapter
import crux.visualisation.domain.TemporalData
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.visualisation.GraphRenderData
import crux.visualisation.domain.visualisation.VisualisationMode
import crux.visualisation.domain.visualisation.VisualisationMode.SimpleColorGraph
import crux.visualisation.exhaustive

@Composable
fun VisualisationPanel(
    visualisationMode: VisualisationMode,
    graphRenderDataSource: () -> GraphRenderData
) {
    when (visualisationMode) {
        SimpleColorGraph -> SimpleColorGraphPanel(graphRenderDataSource())
    }.exhaustive
}