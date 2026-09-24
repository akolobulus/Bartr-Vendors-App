package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BartrMapCanvas
import com.example.ui.theme.BartrBackdrop
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrBlueDark
import com.example.ui.theme.BartrBlueTint
import com.example.ui.theme.BartrDanger
import com.example.ui.theme.BartrInk
import com.example.ui.theme.BartrInkSoft
import com.example.ui.theme.BartrSuccess
import com.example.viewmodel.BartrUiState

@Composable
fun ActiveJobScreen(
    state: BartrUiState,
    onBack: () -> Unit,
    onOpenChat: () -> Unit,
    onAdvanceStage: () -> Unit,
    onCancelJob: () -> Unit,
    onShowAlert: (String) -> Unit
) {
    val request = state.activeRequest ?: return

    val (headline, subheadline, buttonLabel) = when (state.activeJobStage) {
        0 -> Triple("Head to the customer", "${request.customer} · ${request.address}", "I've arrived")
        1 -> Triple("You've arrived", "Let the customer know you're starting", "Start job")
        else -> Triple("Job in progress", request.customer, "Mark job complete")
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // 1. Top Route Map
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.42f)
        ) {
            BartrMapCanvas(
                activeRequest = request,
                isRouteMode = true
            )

            // Back button
            Surface(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 20.dp, top = 14.dp)
                    .size(44.dp)
                    .shadow(10.dp, CircleShape)
                    .clip(CircleShape)
                    .testTag("active_back_button"),
                color = Color.White
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = BartrInk,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // 2. Bottom Sheet Content
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.58f)
                .shadow(16.dp, RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)),
            shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
                    .navigationBarsPadding()
            ) {
                // Drag handle
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(40.dp)
                        .height(5.dp)
                        .clip(RoundedCornerShape(99.dp))
                        .background(BartrInk.copy(alpha = 0.15f))
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Stage headline
                Text(
                    text = headline,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = BartrInk
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = subheadline,
                    fontSize = 14.sp,
                    color = BartrInkSoft
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Peer action row (Customer avatar, Chat, Safety)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Customer Avatar Column
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            modifier = Modifier.size(46.dp),
                            shape = CircleShape,
                            color = BartrBackdrop
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = request.custInitial,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BartrInk
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = request.customer,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = BartrInkSoft
                        )
                    }

                    // Chat Action Column
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onOpenChat() }
                            .testTag("open_chat_button")
                    ) {
                        Surface(
                            modifier = Modifier.size(46.dp),
                            shape = CircleShape,
                            color = BartrBlueTint
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Chat,
                                    contentDescription = "Chat",
                                    tint = BartrBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Chat",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = BartrInkSoft
                        )
                    }

                    // Safety Action Column
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onShowAlert("Report a safety concern is coming soon.") }
                    ) {
                        Surface(
                            modifier = Modifier.size(46.dp),
                            shape = CircleShape,
                            color = BartrBlueTint
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = "Safety",
                                    tint = BartrBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Safety",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = BartrInkSoft
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Divider(color = BartrInk.copy(alpha = 0.08f))
                Spacer(modifier = Modifier.height(12.dp))

                // Address row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Address",
                        tint = BartrInkSoft,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = request.address,
                        fontSize = 14.sp,
                        color = BartrInk
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Pay line
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = "Cash payment",
                        tint = BartrSuccess,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = "Cash",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = BartrInk,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = request.price,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BartrInk
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                Divider(color = BartrInk.copy(alpha = 0.08f))
                Spacer(modifier = Modifier.height(14.dp))

                // Primary advance stage button
                Button(
                    onClick = onAdvanceStage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("advance_stage_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BartrBlue,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = buttonLabel,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Cancel job row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCancelJob() }
                        .padding(vertical = 10.dp)
                        .testTag("cancel_job_button"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Block,
                        contentDescription = "Cancel job",
                        tint = BartrDanger,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = "Cancel job",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = BartrDanger
                    )
                }
            }
        }
    }
}
