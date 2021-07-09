@file:Suppress("FunctionName")

package crux.visualisation.components.visualisation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import crux.visualisation.MutableDerivative
import crux.visualisation.components.generic.ButtonStyle.Filled
import crux.visualisation.components.generic.ButtonStyle.Outlined
import crux.visualisation.components.generic.MyButton
import crux.visualisation.components.generic.MySpacer
import crux.visualisation.components.generic.getComponentWidth
import crux.visualisation.domain.visualisation.VisualisationData
import crux.visualisation.domain.visualisation.VisualisationMode
import crux.visualisation.domain.visualisation.toVisualisationMode

@Composable
fun VisualisationDataPanel(
    width: Dp,
    mutableVisualisationData: MutableDerivative<VisualisationData>
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MySpacer()

        VisualisationModePanel(
            width,
            mutableVisualisationData.toVisualisationMode()
        )

        MySpacer()
    }
}

@Composable
fun VisualisationModePanel(
    width: Dp,
    mutableVisualisationMode: MutableDerivative<VisualisationMode>
) {
    val visualisationModes = VisualisationMode.values()
    val buttonWidth = getComponentWidth(width, visualisationModes.size)

    Row {
        MySpacer()
        visualisationModes.forEach {
            VisualisationModeButton(
                buttonWidth,
                mutableVisualisationMode,
                it
            )
            MySpacer()
        }
    }
}

@Composable
fun VisualisationModeButton(
    width: Dp,
    mutableMode: MutableDerivative<VisualisationMode>,
    mode: VisualisationMode
) {
    MyButton(
        if (mode == mutableMode.value) Filled else Outlined,
        width,
        label = mode.name
    ) { mutableMode.set(mode) }
}