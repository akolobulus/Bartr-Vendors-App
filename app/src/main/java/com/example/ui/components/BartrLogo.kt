package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BartrLogoMark(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    topColor: Color = Color.White,
    bottomColor: Color = Color.White.copy(alpha = 0.55f)
) {
    Canvas(modifier = modifier.size(size)) {
        val s = this.size.width / 32f

        // Top arrow: M6 12L14 6L14 10L26 10V14L14 14V18L6 12Z
        val topPath = Path().apply {
            moveTo(6f * s, 12f * s)
            lineTo(14f * s, 6f * s)
            lineTo(14f * s, 10f * s)
            lineTo(26f * s, 10f * s)
            lineTo(26f * s, 14f * s)
            lineTo(14f * s, 14f * s)
            lineTo(14f * s, 18f * s)
            close()
        }
        drawPath(topPath, color = topColor)

        // Bottom arrow: M26 20L18 26L18 22L6 22V18L18 18V14L26 20Z
        val bottomPath = Path().apply {
            moveTo(26f * s, 20f * s)
            lineTo(18f * s, 26f * s)
            lineTo(18f * s, 22f * s)
            lineTo(6f * s, 22f * s)
            lineTo(6f * s, 18f * s)
            lineTo(18f * s, 18f * s)
            lineTo(18f * s, 14f * s)
            close()
        }
        drawPath(bottomPath, color = bottomColor)
    }
}
