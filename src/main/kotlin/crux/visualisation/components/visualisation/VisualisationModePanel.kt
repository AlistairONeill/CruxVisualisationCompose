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
import crux.visualisation.domain.visualisation.*
import crux.visualisation.domain.visualisation.VisualisationData.NetworkGraphData

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

        if (mutableVisualisationData.value is NetworkGraphData) {
            MySpacer()

            QuerySelectorPanel(
                width,
                mutableVisualisationData.toQuery()
            )
        }

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

@Composable
fun QuerySelectorPanel(
    width: Dp,
    mutableQuery: MutableDerivative<VisualisationQuery>
) {
    val queries = VisualisationQuery.values()
    val buttonWidth = getComponentWidth(width, queries.size)

    Row {
        MySpacer()
        queries.forEach {
            QuerySelectorButton(
                buttonWidth,
                mutableQuery,
                it
            )
            MySpacer()
        }
    }
}

@Composable
fun QuerySelectorButton(
    width: Dp,
    mutableMode: MutableDerivative<VisualisationQuery>,
    mode: VisualisationQuery
) {
    MyButton(
        if (mode == mutableMode.value) Filled else Outlined,
        width,
        label = mode.name
    ) { mutableMode.set(mode) }
}