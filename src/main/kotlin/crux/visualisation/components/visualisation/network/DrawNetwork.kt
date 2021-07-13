package crux.visualisation.components.visualisation.network

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import crux.visualisation.domain.VisualisationColor
import crux.visualisation.domain.renderColor
import crux.visualisation.domain.visualisation.NetworkRenderData
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.*

private const val radius = 100f
private const val deselectedWidth = 10f
private const val selectedWidth = 20f

internal fun DrawScope.drawNetwork(
    renderData: NetworkRenderData,
    positionToColor: AtomicReference<((Offset) -> VisualisationColor?)?>
) {
    val height = size.height
    val width = size.width

    val bigRadius = (min(height, width) * 0.30)

    val positions = renderData.colors.mapIndexed { index, color ->
        val angle = 2 * PI * index / renderData.colors.size
        val x = width / 2 + bigRadius * cos(angle)
        val y = height / 2 + bigRadius * sin(angle)
        color to Offset(x.toFloat(), y.toFloat())
    }.toMap()

    positionToColor.set { offset ->
        positions.entries.firstOrNull { (_, position) ->
            distance(position, offset) < radius
        }?.key
    }

    for (link in renderData.links) {
        val from = positions[link.from] ?: continue
        val to = positions[link.to] ?: continue

        drawArrow(
            if (renderData.highlightedLinks.contains(link)) Color.Green else Color.Black,
            from,
            to,
            50f,
            10f,
            200f
        )
    }

    positions.forEach { (colour, offset) ->
        drawCircle(
            colour.renderColor.let(::Color),
            radius,
            offset
        )

        drawCircle(
            when {
                colour == renderData.selectedColor -> Color.Black
                renderData.highlightedColours.contains(colour) -> Color.Green
                else -> Color.DarkGray
            },
            radius,
            offset,
            style = Stroke(
                if (colour == renderData.selectedColor || renderData.highlightedColours.contains(colour))
                    selectedWidth
                else
                    deselectedWidth
            )
        )
    }
}

private fun DrawScope.drawArrow(
    color: Color,
    start: Offset,
    end: Offset,
    headSize: Float,
    strokeWidth: Float,
    curve: Float = 0f
) {
    val dx = end.x - start.x
    val dy = end.y - start.y
    val theta = atan2(dy, dx)

    val orth = theta + PI / 2
    val mx = (start.x + end.x) / 2 + curve * cos(orth).toFloat()
    val my = (start.y + end.y) / 2 + curve * sin(orth).toFloat()

    val mmx = (start.x + end.x) / 2 + curve * cos(orth).toFloat() / 2
    val mmy = (start.y + end.y) / 2 + curve * sin(orth).toFloat() / 2

    val x1 = mmx - headSize * cos(theta + PI / 4).toFloat()
    val x2 = mmx - headSize * cos(theta - PI / 4).toFloat()
    val y1 = mmy - headSize * sin(theta + PI / 4).toFloat()
    val y2 = mmy - headSize * sin(theta - PI / 4).toFloat()

    drawPath(
        path = Path().apply {
            moveTo(start.x, start.y)
            quadraticBezierTo(
                mx, my,
                end.x, end.y
            )
            moveTo(mmx, mmy)
            lineTo(x1, y1)
            moveTo(mmx, mmy)
            lineTo(x2, y2)
        },
        color = color,
        style = Stroke(strokeWidth)
    )
}

private fun distance(offset1: Offset, offset2: Offset): Float {
    val dx = offset1.x - offset2.x
    val dy = offset1.y - offset2.y
    return sqrt(dx * dx + dy * dy)
}