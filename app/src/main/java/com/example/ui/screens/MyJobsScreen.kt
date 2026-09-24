package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
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
import com.example.ui.theme.BartrBlueTint
import com.example.ui.theme.BartrInk
import com.example.ui.theme.BartrInkSoft
import com.example.viewmodel.BartrUiState

@Composable
fun MyJobsScreen(
    state: BartrUiState,
    onBack: () -> Unit
) {
    val grouped = state.jobHistory.groupBy { it.monthSection }

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
            text = "My jobs",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = BartrInk,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            grouped.forEach { (month, jobs) ->
                item {
                    Text(
                        text = month,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BartrInk,
                        modifier = Modifier.padding(top = 18.dp, bottom = 4.dp)
                    )
                }

                items(jobs) { job ->
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
                                text = "${job.customer} · ${job.status}",
                                fontSize = 13.sp,
                                color = BartrInkSoft
                            )
                        }

                        Text(
                            text = job.price,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = BartrInk
                        )
                    }
                    Divider(color = BartrInk.copy(alpha = 0.08f))
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}
