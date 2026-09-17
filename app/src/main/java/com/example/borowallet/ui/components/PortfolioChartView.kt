package com.example.borowallet.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borowallet.model.HistoricalPoint
import com.example.borowallet.ui.theme.BoroPrimaryVariant
import java.util.Locale

@Composable
fun PortfolioChartView(
    historicalData: List<HistoricalPoint>,
    selectedTimeframe: String,
    onSelectTimeframe: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val timeframes = listOf("1D", "1W", "1M", "1Y", "ALL")
    var hoveredIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        // Scrubber Tooltip Preview
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(26.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (hoveredIndex != null && hoveredIndex in historicalData.indices) {
                val point = historicalData[hoveredIndex!!]
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "$%,.2f".format(Locale.US, point.value),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = point.time,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Canvas Chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(historicalData) {
                        detectDragGestures(
                            onDragEnd = { hoveredIndex = null },
                            onDragCancel = { hoveredIndex = null },
                            onDrag = { change, _ ->
                                change.consume()
                                val x = change.position.x
                                val step = size.width / (historicalData.size - 1).coerceAtLeast(1)
                                val idx = (x / step).toInt().coerceIn(0, historicalData.lastIndex)
                                hoveredIndex = idx
                            }
                        )
                    }
                    .pointerInput(historicalData) {
                        detectTapGestures(
                            onPress = { offset ->
                                val step = size.width / (historicalData.size - 1).coerceAtLeast(1)
                                val idx = (offset.x / step).toInt().coerceIn(0, historicalData.lastIndex)
                                hoveredIndex = idx
                                tryAwaitRelease()
                                hoveredIndex = null
                            }
                        )
                    }
                    .testTag("portfolio_canvas_chart")
            ) {
                if (historicalData.size < 2) return@Canvas

                val minVal = historicalData.minOf { it.value } * 0.99
                val maxVal = historicalData.maxOf { it.value } * 1.01
                val valueRange = (maxVal - minVal).coerceAtLeast(1.0)

                val points = historicalData.mapIndexed { index, point ->
                    val x = index.toFloat() / (historicalData.size - 1) * size.width
                    val y = size.height - ((point.value - minVal) / valueRange * size.height).toFloat()
                    Offset(x, y.coerceIn(8f, size.height - 8f))
                }

                // Smooth cubic bezier path
                val linePath = Path().apply {
                    moveTo(points[0].x, points[0].y)
                    for (i in 0 until points.size - 1) {
                        val p0 = points[i]
                        val p1 = points[i + 1]
                        val controlX = (p0.x + p1.x) / 2f
                        cubicTo(controlX, p0.y, controlX, p1.y, p1.x, p1.y)
                    }
                }

                // Gradient area path under curve
                val fillPath = Path().apply {
                    addPath(linePath)
                    lineTo(points.last().x, size.height)
                    lineTo(points.first().x, size.height)
                    close()
                }

                val gradientBrush = Brush.verticalGradient(
                    colors = listOf(
                        BoroPrimaryVariant.copy(alpha = 0.25f),
                        BoroPrimaryVariant.copy(alpha = 0.0f)
                    ),
                    startY = 0f,
                    endY = size.height
                )

                drawPath(fillPath, brush = gradientBrush)

                // Draw main stroke
                drawPath(
                    path = linePath,
                    color = BoroPrimaryVariant,
                    style = Stroke(
                        width = 3.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )

                // Scrubber guide and indicator point
                hoveredIndex?.let { idx ->
                    if (idx in points.indices) {
                        val activePoint = points[idx]
                        drawLine(
                            color = BoroPrimaryVariant.copy(alpha = 0.5f),
                            start = Offset(activePoint.x, 0f),
                            end = Offset(activePoint.x, size.height),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        )

                        // Outer ring
                        drawCircle(
                            color = BoroPrimaryVariant.copy(alpha = 0.2f),
                            radius = 9.dp.toPx(),
                            center = activePoint
                        )
                        // Inner dot
                        drawCircle(
                            color = BoroPrimaryVariant,
                            radius = 5.dp.toPx(),
                            center = activePoint
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 2.5.dp.toPx(),
                            center = activePoint
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Timeframe Pill Selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            timeframes.forEach { tf ->
                val isSelected = selectedTimeframe == tf
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(34.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.onSurface else Color.Transparent
                        )
                        .clickable { onSelectTimeframe(tf) }
                        .testTag("timeframe_pill_$tf"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tf,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
