package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Official Mauritania National Flag
 * - Green background (#006233)
 * - Top & Bottom red horizontal stripes (#C8102E) - 20% height each
 * - Center Gold Crescent & 5-pointed Star (#FFC400) facing upward
 */
@Composable
fun MauritaniaFlag(
  modifier: Modifier = Modifier,
  width: Dp = 36.dp,
  height: Dp = 24.dp
) {
  val greenColor = Color(0xFF006233)
  val redColor = Color(0xFFC8102E)
  val goldColor = Color(0xFFFFC400)

  Box(
    modifier = modifier
      .width(width)
      .height(height)
      .clip(RoundedCornerShape(3.dp))
      .border(0.5.dp, Color(0x33000000), RoundedCornerShape(3.dp))
  ) {
    Canvas(modifier = Modifier.matchParentSize()) {
      val w = size.width
      val h = size.height

      // 1. Top Red Stripe (20% height)
      drawRect(
        color = redColor,
        topLeft = Offset(0f, 0f),
        size = Size(w, h * 0.20f)
      )

      // 2. Middle Green Section (60% height)
      drawRect(
        color = greenColor,
        topLeft = Offset(0f, h * 0.20f),
        size = Size(w, h * 0.60f)
      )

      // 3. Bottom Red Stripe (20% height)
      drawRect(
        color = redColor,
        topLeft = Offset(0f, h * 0.80f),
        size = Size(w, h * 0.20f)
      )

      // Center of flag
      val centerX = w / 2f
      val centerY = h / 2f + h * 0.02f

      // 4. Crescent facing UP in center
      drawUpwardCrescent(
        centerX = centerX,
        centerY = centerY,
        outerRadius = h * 0.18f,
        innerRadius = h * 0.14f,
        color = goldColor
      )

      // 5. Gold Star above crescent
      drawFivePointStar(
        centerX = centerX,
        centerY = centerY - h * 0.09f,
        radius = h * 0.065f,
        color = goldColor
      )
    }
  }
}

private fun DrawScope.drawUpwardCrescent(
  centerX: Float,
  centerY: Float,
  outerRadius: Float,
  innerRadius: Float,
  color: Color
) {
  // Upward facing crescent: path combining outer arc and inner offset arc
  val path = Path().apply {
    val outerPath = Path().apply {
      addOval(
        androidx.compose.ui.geometry.Rect(
          centerX - outerRadius,
          centerY - outerRadius * 0.6f,
          centerX + outerRadius,
          centerY + outerRadius * 0.9f
        )
      )
    }
    val innerPath = Path().apply {
      addOval(
        androidx.compose.ui.geometry.Rect(
          centerX - innerRadius,
          centerY - innerRadius * 0.9f,
          centerX + innerRadius,
          centerY + innerRadius * 0.5f
        )
      )
    }
    // Simple shape approximation for upward crescent
    addPath(outerPath)
  }

  // Draw full gold oval then cut out top with green inner oval
  drawCircle(
    color = color,
    radius = outerRadius,
    center = Offset(centerX, centerY + outerRadius * 0.1f)
  )
  drawCircle(
    color = Color(0xFF006233),
    radius = outerRadius * 0.82f,
    center = Offset(centerX, centerY - outerRadius * 0.22f)
  )
}

private fun DrawScope.drawFivePointStar(
  centerX: Float,
  centerY: Float,
  radius: Float,
  color: Color
) {
  val path = Path()
  val innerRadius = radius * 0.4f
  var angle = -PI.toFloat() / 2f
  val angleStep = (PI.toFloat() * 2f) / 10f

  for (i in 0 until 10) {
    val r = if (i % 2 == 0) radius else innerRadius
    val x = centerX + r * cos(angle)
    val y = centerY + r * sin(angle)
    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    angle += angleStep
  }
  path.close()

  drawPath(path = path, color = color)
}
