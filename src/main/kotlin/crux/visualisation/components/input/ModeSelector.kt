@file:Suppress("FunctionName")

package crux.visualisation.components.input

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import crux.visualisation.MutableDerivative
import crux.visualisation.components.generic.ButtonStyle
import crux.visualisation.components.generic.MyButton
import crux.visualisation.components.generic.MySpacer
import crux.visualisation.components.generic.getComponentWidth
import crux.visualisation.domain.input.Mode

@Composable
fun ModeSelector(
    width: Dp,
    mutableMode: MutableDerivative<Mode>
) {
    val modes = Mode.values()

    val buttonWidth = getComponentWidth(width, modes.size)

    Row {
        MySpacer()
        modes.forEach {
            ModeButton(
                buttonWidth,
                mutableMode,
                it
            )
            MySpacer()
        }
    }
}

@Composable
fun ModeButton(
    width: Dp,
    mutableMode: MutableDerivative<Mode>,
    mode: Mode
) {
    MyButton(
        if (mode == mutableMode.value) ButtonStyle.Filled else ButtonStyle.Outlined,
        width,
        label = mode.name
    ) { mutableMode.set(mode) }
}