@file:Suppress("FunctionName")

package crux.visualisation.components.input

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import crux.visualisation.MutableDerivative
import crux.visualisation.domain.input.InputData
import crux.visualisation.domain.input.InputData.*
import crux.visualisation.domain.input.withColor
import crux.visualisation.domain.input.withFrom
import crux.visualisation.domain.input.withTo
import crux.visualisation.exhaustive

@Composable
fun InputDataPanel(
    width: Dp,
    mutableInputData: MutableDerivative<InputData>
) {
    val (inputData, setInputData) = mutableInputData
    inputData.run {
        when (this) {
            is None -> {}
            is Color -> {
                ColorPanel(
                    width,
                    color,
                ) { setInputData(withColor(it)) }
            }
            is Link -> {
                ColorPanel(
                    width,
                    from
                ) { setInputData(withFrom(it)) }

                Icon(Icons.Default.KeyboardArrowDown, null)

                ColorPanel(
                    width,
                    to
                ) { setInputData(withTo(it)) }
            }
        }.exhaustive
    }

}