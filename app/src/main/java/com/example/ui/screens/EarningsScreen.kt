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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BartrBackdrop
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrBlueDark
import com.example.ui.theme.BartrBlueTint
import com.example.ui.theme.BartrInk
import com.example.ui.theme.BartrInkSoft
import com.example.ui.theme.BartrSuccess
import com.example.viewmodel.BartrUiState
import java.text.NumberFormat
import java.util.Locale

@Composable
fun EarningsScreen(
    state: BartrUiState,
    onBack: () -> Unit,
    onToggleBalance: () -> Unit,
    onWithdraw: () -> Unit
) {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    val displayBalance = if (state.balanceHidden) "••••••" else "₦${formatter.format(state.availableBalance)}"

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
            text = "Earnings",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = BartrInk,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Blue Balance Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .testTag("balance_card"),
                color = BartrBlue
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Available balance",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                        IconButton(
                            onClick = onToggleBalance,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = if (state.balanceHidden) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Show or hide balance",
                                tint = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = displayBalance,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = onWithdraw,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = BartrBlueDark
                        ),
                        modifier = Modifier.testTag("withdraw_button")
                    ) {
                        Text(
                            text = "Withdraw",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Stat pair
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatBox(
                    number = state.weekJobs.toString(),
                    label = "Jobs this week",
                    modifier = Modifier.weight(1f)
                )
                StatBox(
                    number = state.weekTotal,
                    label = "Earned this week",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Recent jobs",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = BartrInk
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Recent jobs list
            state.jobHistory.take(5).forEachIndexed { index, job ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        color = BartrBlueTint
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = "Job",
                                tint = BartrBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = job.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BartrInk
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${job.customer} · ${job.dateOrMonth}",
                            fontSize = 13.sp,
                            color = BartrInkSoft
                        )
                    }

                    Text(
                        text = "+${job.price}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BartrSuccess
                    )
                }
                if (index < state.jobHistory.size - 1) {
                    Divider(color = BartrInk.copy(alpha = 0.08f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StatBox(
    number: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        color = BartrBackdrop
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = number,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BartrInk
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                color = BartrInkSoft
            )
        }
    }
}
