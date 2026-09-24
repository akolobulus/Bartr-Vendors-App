package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BartrBackdrop
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrBlueDark
import com.example.ui.theme.BartrBlueTint
import com.example.ui.theme.BartrDanger
import com.example.ui.theme.BartrGreenTint
import com.example.ui.theme.BartrInk
import com.example.ui.theme.BartrInkSoft
import com.example.ui.theme.BartrRedTint
import com.example.ui.theme.BartrYellowTint
import com.example.viewmodel.BartrUiState

@Composable
fun RequestDetailScreen(
    state: BartrUiState,
    onDecline: () -> Unit,
    onAccept: () -> Unit,
    onBack: () -> Unit
) {
    val request = state.activeRequest ?: return
    val isTaken = request.isTaken || state.isRequestTakenBannerVisible

    val minutes = state.countdownSeconds / 60
    val seconds = state.countdownSeconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)
    val progressFraction = (state.countdownSeconds / 600f).coerceIn(0f, 1f)

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
                IconButton(onClick = onDecline) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Decline",
                        tint = BartrInk,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = "Job offer",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BartrInk
            )
        }

        // Body
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
            // Countdown progress
            if (!isTaken) {
                Column(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Respond before it goes to someone else",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = BartrInkSoft
                        )
                        Text(
                            text = timeFormatted,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = BartrDanger
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(RoundedCornerShape(99.dp))
                            .background(BartrBackdrop)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progressFraction)
                                .height(5.dp)
                                .clip(RoundedCornerShape(99.dp))
                                .background(BartrBlue)
                        )
                    }
                }
            } else {
                // Taken banner notice
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    color = BartrRedTint
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Taken alert",
                            tint = BartrDanger,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "This job has already been taken by another vendor.",
                            fontSize = 14.sp,
                            color = BartrInk
                        )
                    }
                }
            }

            // Customer request description bubble
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 4.dp)),
                color = BartrBackdrop
            ) {
                Text(
                    text = "\"${request.text}\"",
                    fontSize = 15.sp,
                    color = BartrInk,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Chip row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = BartrBlueTint
                ) {
                    Text(
                        text = request.category,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BartrBlueDark,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = BartrYellowTint
                ) {
                    Text(
                        text = request.urgency,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8A7A00),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Customer mini card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                color = BartrBackdrop
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        color = BartrBlueTint
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = request.custInitial,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = BartrBlue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = request.customer,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = BartrInk
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = request.custMeta,
                            fontSize = 12.sp,
                            color = BartrInkSoft
                        )
                    }

                    Text(
                        text = request.distance,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BartrInkSoft
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Price card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                color = BartrGreenTint
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Offered price",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2F7A52)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = request.price,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = BartrInk
                    )
                }
            }
        }

        // Bottom fixed action buttons
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            color = Color.White
        ) {
            if (isTaken) {
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("taken_back_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BartrBackdrop,
                        contentColor = BartrInk
                    )
                ) {
                    Text("Back to job offers", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDecline,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .testTag("decline_button"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BartrBackdrop,
                            contentColor = BartrInk
                        )
                    ) {
                        Text("Decline", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Button(
                        onClick = onAccept,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .testTag("accept_button"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BartrBlue,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Accept", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
