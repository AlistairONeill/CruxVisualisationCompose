@file:Suppress("FunctionName")

package crux.visualisation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import crux.visualisation.components.generic.SidePanels
import crux.visualisation.components.visualisation.VisualisationPanel
import crux.visualisation.domain.TemporalData
import crux.visualisation.domain.toInputAcceptor
import crux.visualisation.domain.toSetVisualisationColor
import crux.visualisation.domain.visualisation.toNetworkRenderDataSource
import crux.visualisation.domain.visualisation.toGraphRenderDataSource
import crux.visualisation.domain.visualisation.toVisualisationMode

@Composable
fun MainComponent(
    temporalData: MutableState<TemporalData>
) {
    VisualisationPanel(
        temporalData.value.visualisationMode,
        temporalData.value.toGraphRenderDataSource(),
        temporalData.value.toNetworkRenderDataSource(),
        temporalData.toSetVisualisationColor()
    )

    SidePanels(
        temporalData.toInputAcceptor(),
        temporalData.value.history,
        temporalData.toVisualisationMode()
    )
}