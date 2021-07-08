package crux.visualisation.components.visualisation.network

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import crux.visualisation.domain.renderColor
import crux.visualisation.domain.visualisation.NetworkRenderData
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun NetworkVisualisationPanel(
    renderData: NetworkRenderData
) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            Modifier
                .fillMaxSize(0.8f)
        ) {
            drawNetwork(
                renderData
            )
        }
    }
}

private const val radius = 100f
private const val STROKE_WIDTH = 15f

private fun DrawScope.drawNetwork(
    renderData: NetworkRenderData
) {
    val height = size.height
    val width = size.width

    val bigRadius = (min(height, width) * 0.35)

    val positions = renderData.colors.mapIndexed { index, color ->
        val angle = 2 * PI * index / renderData.colors.size
        val x = width / 2 + bigRadius * cos(angle)
        val y = height / 2 + bigRadius * sin(angle)
        color to Offset(x.toFloat(), y.toFloat())
    }.toMap()

    positions.forEach { (colour, offset) ->
        drawCircle(
            colour.renderColor.let(::Color),
            radius,
            offset
        )

        drawCircle(
            Color.Black,
            radius,
            offset,
            style = Stroke(STROKE_WIDTH)
        )
    }
}