package crux.visualisation.components.visualisation.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import crux.visualisation.domain.visualisation.GraphRenderData
import java.time.LocalTime
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SimpleColorGraphPanel(
    renderData: GraphRenderData
) {
    val (mouseCoords, setMouseCoords) = remember { mutableStateOf<Offset?>(null) }
    val (timeRealizer, setTimeRealizer) = remember { mutableStateOf<TimeRealizer?>(null) }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            Modifier
                .fillMaxSize(0.8f)
                .pointerMoveFilter(
                    onMove = {
                        setMouseCoords(it)
                        false
                    }
                )
        ) {
            drawGraph(
                renderData,
                mouseCoords,
                setTimeRealizer
            )
        }
    }


    if (mouseCoords != null && timeRealizer != null) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .fillMaxSize(0.8f)
            ) {
                Card (
                    Modifier
                        //TODO
                        .offset(
                            x = mouseCoords.x.dp + 20.dp,
                            y = mouseCoords.y.dp + 20.dp
                        )
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    val (validTime, transactionTime) = timeRealizer(mouseCoords)
                    Column(Modifier.padding(16.dp)) {
                        Row {
                            Text("Valid Time: ")
                            Text(validTime.toString())
                        }
                        Row {
                            Text("Transaction Time: ")
                            Text(transactionTime.toString())
                        }
                    }
                }
            }
        }
    }
}

private typealias TimeRealizer = Offset.() -> Pair<LocalTime, LocalTime>

fun DrawScope.drawGraph(
    renderData: GraphRenderData,
    mousePosition: Offset?,
    setTimeRealizer: (TimeRealizer) -> Unit
) {
    val height = size.height
    val width = size.width

    val borderOffset = 20f

    if (renderData.transactionTimes.size > 1
        && renderData.validTimes.size > 1
    ) {
        val dataHeight = height - 2 * borderOffset
        val dataWidth = width - 2 * borderOffset

        val minTransactionTime = renderData.transactionTimes.minOrNull()!!.toSecondOfDay()
        val minValidTime = renderData.validTimes.minOrNull()!!.toSecondOfDay()

        val transactionTimeDiff = renderData.transactionTimes.maxOrNull()!!.toSecondOfDay() - minTransactionTime
        val validTimeDiff = renderData.validTimes.maxOrNull()!!.toSecondOfDay() - minValidTime

        val heightScale = dataHeight / validTimeDiff
        val widthScale = dataWidth / transactionTimeDiff

        fun x(time: LocalTime): Float = borderOffset + (time.toSecondOfDay() - minTransactionTime) * widthScale
        fun y(time: LocalTime): Float = height - borderOffset - (time.toSecondOfDay() - minValidTime) * heightScale

        for (i in 0 until renderData.validTimes.size - 1) {
            val y1 = y(renderData.validTimes[i])
            val y2 = y(renderData.validTimes[i + 1])

            for (j in 0 until renderData.transactionTimes.size - 1) {
                val color = renderData.data[i][j] ?: continue
                val x1 = x(renderData.transactionTimes[j])
                val x2 = x(renderData.transactionTimes[j + 1])
                drawRect(color, Offset(x1, y2), Size(x2 - x1, y1 - y2))
            }

            val color = renderData.data[i].last() ?: continue
            val x1 = x(renderData.transactionTimes.last())
            val x2 = x1 + borderOffset

            drawRect(color, Offset(x1, y2), Size(x2 - x1, y1 - y2))
        }
        val color = renderData.data.last().last()
        if (color != null) {
            val x1 = x(renderData.transactionTimes.last())
            val x2 = x1 + borderOffset
            val y1 = y(renderData.validTimes.last())
            val y2 = y1 - borderOffset
            drawRect(color, Offset(x1, y2), Size(x2 - x1, y1 - y2))
        }

        setTimeRealizer {
            ((-y + height - borderOffset) / heightScale + minValidTime).toLong().let(LocalTime::ofSecondOfDay) to
                    ((x - borderOffset) / widthScale + minTransactionTime).toLong().let(LocalTime::ofSecondOfDay)
        }
    }

    drawArrow(
        Color.Black,
        Offset(0f, height - borderOffset),
        Offset(width - borderOffset, height - borderOffset),
        borderOffset,
        5f
    )

    drawArrow(
        Color.Black,
        Offset(borderOffset, height),
        Offset(borderOffset, borderOffset),
        borderOffset,
        5f
    )

    if (mousePosition != null) {
        val mouseX = mousePosition.x
        val mouseY = mousePosition.y

        drawLine(Color.White, Offset(mouseX, 0f), Offset(mouseX, height), strokeWidth = 4f)
        drawLine(Color.White, Offset(0f, mouseY), Offset(width, mouseY), strokeWidth = 4f)
    }
}

fun DrawScope.drawArrow(
    color: Color,
    start: Offset,
    end: Offset,
    headSize: Float,
    strokeWidth: Float
) {
    val dx = end.x - start.x
    val dy = end.y - start.y
    val theta = atan2(dy, dx)
    val x1 = end.x - headSize * cos(theta + PI / 4).toFloat()
    val x2 = end.x - headSize * cos(theta - PI / 4).toFloat()
    val y1 = end.y - headSize * sin(theta + PI / 4).toFloat()
    val y2 = end.y - headSize * sin(theta - PI / 4).toFloat()

    drawPath(
        path = Path().apply {
            moveTo(start.x, start.y)
            lineTo(end.x, end.y)
            lineTo(x1, y1)
            moveTo(end.x, end.y)
            lineTo(x2, y2)
        },
        color = color,
        style = Stroke(strokeWidth)
    )
}