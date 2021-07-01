@file:Suppress("FunctionName", "FunctionName", "FunctionName", "FunctionName")

package crux.visualisation.components.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import crux.visualisation.components.generic.MySpacer
import crux.visualisation.components.generic.getComponentWidth
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.renderColor

@Composable
fun ColorPanel(
    width: Dp,
    currentColor: VisualisationColor,
    setColor: (VisualisationColor) -> Unit
) {
    val colors = VisualisationColor.values().toList()
    val itemWidth = getComponentWidth(width, colors.size / 2)

    Column {
        colors.chunked(4).forEach { row ->
            ColorPanelRow(
                itemWidth,
                row,
                currentColor,
                setColor
            )

            MySpacer()
        }
    }
}

@Composable
private fun ColorPanelRow(
    itemWidth: Dp,
    colors: List<VisualisationColor>,
    currentColor: VisualisationColor,
    setColor: (VisualisationColor) -> Unit
) {
    Row {
        MySpacer()
        colors.forEach { color ->
            ColorPanelItem(
                itemWidth,
                color,
                currentColor,
                setColor
            )
            MySpacer()
        }
    }
}

@Composable
private fun ColorPanelItem(
    width: Dp,
    color: VisualisationColor,
    currentColor: VisualisationColor,
    setColor: (VisualisationColor) -> Unit
) {
    Surface(
        color = Color(color.renderColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            Modifier
                .size(width)
                .clickable { setColor(color) },
            contentAlignment = Alignment.Center
        ) {
            if (color == currentColor) {
                Icon(
                    Icons.Default.Check,
                    null,
                    tint = Color.White
                )
            }
        }
    }
}


