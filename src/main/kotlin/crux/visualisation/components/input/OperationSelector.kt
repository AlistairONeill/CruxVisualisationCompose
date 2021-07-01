@file:Suppress("FunctionName")

package crux.visualisation.components.input

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import crux.visualisation.MutableDerivative
import crux.visualisation.components.generic.ButtonStyle.Filled
import crux.visualisation.components.generic.ButtonStyle.Outlined
import crux.visualisation.components.generic.MyButton
import crux.visualisation.components.generic.MySpacer
import crux.visualisation.components.generic.getComponentWidth
import crux.visualisation.domain.input.Operation
import crux.visualisation.domain.input.Operation.DELETE
import crux.visualisation.domain.input.Operation.PUT


@Composable
fun OperationSelector(
    width: Dp,
    mutableOperation: MutableDerivative<Operation>
) {
    val operations = Operation.values()

    val buttonWidth = getComponentWidth(width, operations.size)

    Row {
        MySpacer()
        operations.forEach {
            OperationButton(
                buttonWidth,
                mutableOperation,
                it
            )
            MySpacer()
        }
    }
}

@Composable
fun OperationButton(
    width: Dp,
    mutableOperation: MutableDerivative<Operation>,
    operation: Operation
) {
    MyButton(
        if (operation == mutableOperation.value) Filled else Outlined,
        width,
        operation.icon,
        operation.name
    ) { mutableOperation.set(operation) }
}

private val Operation.icon get() = when (this) {
    PUT -> Icons.Default.Add
    DELETE -> Icons.Default.Delete
}