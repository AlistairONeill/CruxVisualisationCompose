@file:Suppress("FunctionName")

package crux.visualisation.components.generic

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import crux.visualisation.components.generic.ButtonStyle.Filled
import crux.visualisation.components.generic.ButtonStyle.Outlined

enum class ButtonStyle {
    Filled, Outlined
}

@Composable
fun MyButton(
    style: ButtonStyle,
    width: Dp?,
    icon: ImageVector? = null,
    label: String,
    onClick: () -> Unit
) {
    val content: @Composable RowScope.() -> Unit = {
        if (icon != null) {
            Icon(icon, null)
            MySpacer()
        }
        Text(label)
    }

    val modifier = width?.let{Modifier.width(it)} ?: Modifier

    when (style) {
        Filled -> Button(
            modifier = modifier,
            onClick = onClick,
            content = content
        )
        Outlined -> OutlinedButton(
            modifier = modifier,
            onClick = onClick,
            content = content
        )
    }
}