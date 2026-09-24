package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.JobOffer
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrBlueDark
import com.example.ui.theme.BartrMapBg
import com.example.ui.theme.BartrStar
import com.example.ui.theme.BartrSuccess
import kotlin.math.roundToInt

@Composable
fun BartrMapCanvas(
    modifier: Modifier = Modifier,
    requests: List<JobOffer> = emptyList(),
    activeRequest: JobOffer? = null,
    isRouteMode: Boolean = false,
    onPinClick: (String) -> Unit = {},
    onRecenter: () -> Unit = {}
) {
    val textMeasurer = rememberTextMeasurer()

    // Pulse animation for vendor pin
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = 54f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseRadius"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseAlpha"
    )

    // Center offset of the vendor in normalized (0..1) coordinates
    val vendorNormX = 0.48f
    val vendorNormY = 0.52f

    Box(modifier = modifier.fillMaxSize().background(BartrMapBg)) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(requests) {
                    detectTapGestures { tapOffset ->
                        val w = size.width
                        val h = size.height

                        // Check if tap is near any pin
                        for (req in requests) {
                            if (req.isTaken) continue
                            val pinX = getNormXForReq(req.id) * w
                            val pinY = getNormYForReq(req.id) * h
                            val dx = tapOffset.x - pinX
                            val dy = tapOffset.y - pinY
                            if (dx * dx + dy * dy < 48.dp.toPx() * 48.dp.toPx()) {
                                onPinClick(req.id)
                                break
                            }
                        }
                    }
                }
        ) {
            val w = size.width
            val h = size.height

            // 1. Draw base blocks & city parks
            drawRoundRect(
                color = Color(0xFFE8ECE3),
                topLeft = Offset(w * 0.05f, h * 0.1f),
                size = Size(w * 0.35f, h * 0.28f),
                cornerRadius = CornerRadius(16f, 16f)
            )
            drawRoundRect(
                color = Color(0xFFE2E8DC), // Green park
                topLeft = Offset(w * 0.52f, h * 0.08f),
                size = Size(w * 0.42f, h * 0.25f),
                cornerRadius = CornerRadius(20f, 20f)
            )
            drawRoundRect(
                color = Color(0xFFE8ECE3),
                topLeft = Offset(w * 0.08f, h * 0.55f),
                size = Size(w * 0.38f, h * 0.38f),
                cornerRadius = CornerRadius(16f, 16f)
            )
            drawRoundRect(
                color = Color(0xFFE5EBE0),
                topLeft = Offset(w * 0.56f, h * 0.52f),
                size = Size(w * 0.38f, h * 0.4f),
                cornerRadius = CornerRadius(16f, 16f)
            )

            // 2. Draw Major Roads
            val roadColor = Color.White
            val roadOutline = Color(0xFFD6DAD0)

            // Market Road (horizontal primary)
            val yMarket = h * 0.44f
            drawLine(roadOutline, Offset(0f, yMarket), Offset(w, yMarket), strokeWidth = 32f)
            drawLine(roadColor, Offset(0f, yMarket), Offset(w, yMarket), strokeWidth = 26f)

            // Allen Avenue (vertical primary)
            val xAllen = w * 0.48f
            drawLine(roadOutline, Offset(xAllen, 0f), Offset(xAllen, h), strokeWidth = 36f)
            drawLine(roadColor, Offset(xAllen, 0f), Offset(xAllen, h), strokeWidth = 30f)

            // Opebi Road (diagonal connector)
            val pathOpebi = Path().apply {
                moveTo(w * 0.1f, h * 0.95f)
                lineTo(w * 0.48f, h * 0.44f)
                lineTo(w * 0.88f, h * 0.05f)
            }
            drawPath(pathOpebi, roadOutline, style = Stroke(width = 24f))
            drawPath(pathOpebi, roadColor, style = Stroke(width = 18f))

            // Toyin Street (secondary horizontal)
            val yToyin = h * 0.78f
            drawLine(roadOutline, Offset(0f, yToyin), Offset(w, yToyin), strokeWidth = 20f)
            drawLine(roadColor, Offset(0f, yToyin), Offset(w, yToyin), strokeWidth = 16f)

            // Awolowo Road
            val yAwolowo = h * 0.22f
            drawLine(roadOutline, Offset(0f, yAwolowo), Offset(w, yAwolowo), strokeWidth = 20f)
            drawLine(roadColor, Offset(0f, yAwolowo), Offset(w, yAwolowo), strokeWidth = 16f)

            // Road Labels
            drawText(
                textMeasurer = textMeasurer,
                text = "Market Rd",
                topLeft = Offset(w * 0.12f, yMarket - 20f),
                style = TextStyle(fontSize = 10.sp, color = Color(0xFF8C96A5), fontWeight = FontWeight.SemiBold)
            )
            drawText(
                textMeasurer = textMeasurer,
                text = "Allen Ave",
                topLeft = Offset(xAllen + 18f, h * 0.32f),
                style = TextStyle(fontSize = 10.sp, color = Color(0xFF8C96A5), fontWeight = FontWeight.SemiBold)
            )
            drawText(
                textMeasurer = textMeasurer,
                text = "Toyin St",
                topLeft = Offset(w * 0.62f, yToyin - 20f),
                style = TextStyle(fontSize = 10.sp, color = Color(0xFF8C96A5), fontWeight = FontWeight.SemiBold)
            )

            val vX = vendorNormX * w
            val vY = vendorNormY * h

            // 3. If in Route Mode (Active Job), draw route line from vendor to customer
            if (isRouteMode && activeRequest != null) {
                val custX = getNormXForReq(activeRequest.id) * w
                val custY = getNormYForReq(activeRequest.id) * h

                val routePath = Path().apply {
                    moveTo(vX, vY)
                    // Mid waypoint along roads
                    val midX = xAllen
                    val midY = custY
                    lineTo(midX, vY)
                    lineTo(midX, midY)
                    lineTo(custX, custY)
                }

                drawPath(
                    path = routePath,
                    color = BartrBlue.copy(alpha = 0.85f),
                    style = Stroke(
                        width = 10f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(24f, 16f), 0f)
                    )
                )
            }

            // 4. Pulse effect around vendor
            drawCircle(
                color = BartrBlue.copy(alpha = pulseAlpha),
                radius = pulseRadius,
                center = Offset(vX, vY)
            )
        }

        // Render vendor marker
        Surface(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (vendorNormX * 1000).roundToInt(), // Handled via box alignment or custom offset
                        (vendorNormY * 1000).roundToInt()
                    )
                }
                .align(Alignment.Center)
                .size(38.dp)
                .shadow(8.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp)),
            color = BartrBlue
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "My Location",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // Render Request Pin overlays for interactive tap & clear visual rendering
        if (!isRouteMode) {
            requests.forEach { req ->
                if (!req.isTaken) {
                    val normX = getNormXForReq(req.id)
                    val normY = getNormYForReq(req.id)

                    val pinColor = when (req.colorType) {
                        "green" -> BartrSuccess
                        "yellow" -> BartrStar
                        else -> BartrBlueDark
                    }

                    val pinIcon = when (req.iconType) {
                        "spa" -> Icons.Default.Face
                        "car" -> Icons.Default.DirectionsCar
                        else -> Icons.Default.Build
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(
                                    x = (normX * 360).dp,
                                    y = (normY * 240).dp
                                )
                                .size(36.dp)
                                .shadow(6.dp, RoundedCornerShape(11.dp))
                                .clip(RoundedCornerShape(11.dp))
                                .background(pinColor)
                                .clickable { onPinClick(req.id) }
                                .testTag("pin_${req.id}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = pinIcon,
                                contentDescription = req.category,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        } else if (activeRequest != null) {
            // Customer pin in route mode
            val normX = getNormXForReq(activeRequest.id)
            val normY = getNormYForReq(activeRequest.id)

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(
                            x = (normX * 360).dp,
                            y = (normY * 240).dp
                        )
                        .size(38.dp)
                        .shadow(8.dp, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(BartrBlueDark),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Customer location",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Recenter button
        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 28.dp, end = 16.dp)
                .size(44.dp)
                .shadow(8.dp, CircleShape)
                .clip(CircleShape)
                .testTag("locate_button"),
            color = Color.White
        ) {
            IconButton(onClick = onRecenter) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "Center on my location",
                    tint = BartrBlue,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// Normalized coordinate placement for demo points in Ikeja
private fun getNormXForReq(id: String): Float = when (id) {
    "r1" -> 0.72f
    "r2" -> 0.22f
    "r3" -> 0.78f
    "r4" -> 0.65f
    "r5" -> 0.18f
    else -> 0.5f
}

private fun getNormYForReq(id: String): Float = when (id) {
    "r1" -> 0.28f
    "r2" -> 0.68f
    "r3" -> 0.78f
    "r4" -> 0.62f
    "r5" -> 0.25f
    else -> 0.5f
}
