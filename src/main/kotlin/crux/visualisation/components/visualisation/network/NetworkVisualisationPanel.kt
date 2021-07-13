package crux.visualisation.components.visualisation.network

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerMoveFilter
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.renderColor
import crux.visualisation.domain.visualisation.NetworkRenderData
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.*

@Suppress("FunctionName")
@Composable
fun NetworkVisualisationPanel(
    renderData: NetworkRenderData,
    setSelectedColor: (VisualisationColor?) -> Unit
) {
    val offsetToColourRef = AtomicReference<((Offset) -> VisualisationColor?)?>(null)
    val mousePositionRef = AtomicReference<Offset?>(null)

    Canvas(
        Modifier
            .fillMaxSize()
            .pointerMoveFilter(
                onMove = {
                    mousePositionRef.set(it)
                    false
                }
            )
            .clickable {
                val offsetToColor = offsetToColourRef.get()
                val mousePosition = mousePositionRef.get()
                if (offsetToColor != null && mousePosition != null) {
                    offsetToColor(mousePosition).let(setSelectedColor)
                }
            }
    ) {
        drawNetwork(
            renderData,
            offsetToColourRef
        )
    }
}
