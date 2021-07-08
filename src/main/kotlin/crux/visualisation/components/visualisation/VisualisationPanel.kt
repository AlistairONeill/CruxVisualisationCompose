@file:Suppress("FunctionName")

package crux.visualisation.components.visualisation

import androidx.compose.runtime.Composable
import crux.visualisation.components.visualisation.graph.SimpleColorGraphPanel
import crux.visualisation.components.visualisation.network.NetworkVisualisationPanel
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.visualisation.GraphRenderData
import crux.visualisation.domain.visualisation.NetworkRenderData
import crux.visualisation.domain.visualisation.VisualisationMode
import crux.visualisation.domain.visualisation.VisualisationMode.SimpleColorGraph
import crux.visualisation.exhaustive

@Composable
fun VisualisationPanel(
    visualisationMode: VisualisationMode,
    graphRenderDataSource: () -> GraphRenderData,
    networkRenderDataSource: () -> NetworkRenderData,
    setSelectedColor: (VisualisationColor?) -> Unit
) {
    when (visualisationMode) {
        SimpleColorGraph -> SimpleColorGraphPanel(graphRenderDataSource())
        VisualisationMode.NetworkGraph -> NetworkVisualisationPanel(
            networkRenderDataSource(),
            setSelectedColor
        )
    }.exhaustive
}