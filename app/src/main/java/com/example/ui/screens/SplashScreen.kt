package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.PlusJakartaSansFontFamily

/**
 * Brand Loading Screen matching 2.png:
 * - Vivid royal blue background (#0066FF)
 * - Centered bold white "Bartr" wordmark
 * - Tagline: "...Let's help you find them."
 * - Minimal, elegant loading indicators at the bottom
 */
@Composable
fun LoadingScreen(
    onDismiss: () -> Unit = {}
) {
    val scale = remember { Animatable(0.94f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(450, easing = FastOutSlowInEasing))
        scale.animateTo(1f, animationSpec = tween(550, easing = FastOutSlowInEasing))
    }

    val infiniteTransition = rememberInfiniteTransition(label = "LoadingDots")
    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1"
    )
    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot2"
    )
    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot3"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0066FF))
            .clickable { onDismiss() }
            .testTag("loading_screen"),
        contentAlignment = Alignment.Center
    ) {
        // Centered Brand Wordmark & Tagline exactly as in 2.png
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = "Bartr",
                color = Color.White,
                fontSize = 62.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PlusJakartaSansFontFamily,
                letterSpacing = (-0.5).sp,
                modifier = Modifier.testTag("loading_brand_title")
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "...Let's help you find them.",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = PlusJakartaSansFontFamily,
                letterSpacing = 0.2.sp,
                modifier = Modifier.testTag("loading_tagline")
            )
        }

        // Subtle bottom loading pulse
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 36.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = dot1Alpha))
            )
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = dot2Alpha))
            )
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = dot3Alpha))
            )
        }
    }
}

/**
 * Alias to support both SplashScreen and LoadingScreen references.
 */
@Composable
fun SplashScreen(
    onDismiss: () -> Unit = {}
) {
    LoadingScreen(onDismiss = onDismiss)
}
