@file:Suppress("FunctionName")

package crux.visualisation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import crux.visualisation.components.generic.SidePanels
import crux.visualisation.components.visualisation.VisualisationPanel
import crux.visualisation.domain.TemporalData

@Composable
fun MainComponent(
    temporalData: MutableState<TemporalData>
) {
    VisualisationPanel(
        temporalData
    )

    SidePanels(temporalData)
}