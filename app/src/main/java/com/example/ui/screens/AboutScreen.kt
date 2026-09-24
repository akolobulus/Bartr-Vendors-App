package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BartrLogoMark
import com.example.ui.theme.BartrBackdrop
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrBlueDark
import com.example.ui.theme.BartrInk
import com.example.ui.theme.BartrInkSoft

@Composable
fun AboutScreen(
    onBack: () -> Unit,
    onShowAlert: (String) -> Unit,
    onPreviewLoadingScreen: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        // Topbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .testTag("back_button"),
                color = BartrBackdrop
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = BartrInk,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Text(
            text = "About",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = BartrInk,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            // Brand Mark
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                BartrLogoMark(
                    size = 38.dp,
                    topColor = BartrBlue,
                    bottomColor = BartrBlueDark
                )
                Text(
                    text = "Bartr for vendors",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = BartrInk
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Version 1.0.0 (91896739)",
                fontSize = 14.sp,
                color = BartrInkSoft
            )

            Spacer(modifier = Modifier.height(24.dp))

            AboutActionCard(
                icon = Icons.Default.Star,
                title = "Rate the app",
                onClick = { onShowAlert("Thanks! App store rating isn't wired up in this prototype.") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            AboutActionCard(
                icon = Icons.Default.ThumbUp,
                title = "Follow us on social media",
                onClick = { onShowAlert("Opening Bartr on social media...") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            AboutActionCard(
                icon = Icons.Default.Description,
                title = "Legal",
                onClick = { onShowAlert("Legal and privacy documents are coming soon.") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            AboutActionCard(
                icon = Icons.Default.PlayArrow,
                title = "Preview loading screen",
                onClick = onPreviewLoadingScreen
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun AboutActionCard(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = BartrBackdrop
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = BartrBlue,
                modifier = Modifier.size(22.dp)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = BartrInk,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
