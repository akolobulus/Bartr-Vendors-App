package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BartrBackdrop
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrInk
import com.example.ui.theme.BartrInkSoft
import com.example.ui.theme.BartrStar
import com.example.viewmodel.BartrUiState

@Composable
fun RatingScreen(
    state: BartrUiState,
    onSubmitRating: (Int, String) -> Unit
) {
    val customerName = state.activeRequest?.customer ?: "the customer"
    var selectedStars by remember { mutableIntStateOf(5) }
    var notesText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "How was the job?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = BartrInk,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Rate your experience with $customerName",
            fontSize = 15.sp,
            color = BartrInkSoft,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        // 5 Star rating buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..5) {
                IconButton(
                    onClick = { selectedStars = i },
                    modifier = Modifier.size(44.dp).testTag("star_$i")
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "$i Stars",
                        tint = if (i <= selectedStars) BartrStar else Color(0x300A2E65),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Feedback notes
        OutlinedTextField(
            value = notesText,
            onValueChange = { notesText = it },
            placeholder = {
                Text(
                    text = "Anything you'd like to add? (optional)",
                    fontSize = 14.sp,
                    color = BartrInkSoft
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .testTag("rating_feedback_input"),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BartrBlue,
                unfocusedBorderColor = Color(0x200A2E65),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = BartrBackdrop,
                focusedTextColor = BartrInk,
                unfocusedTextColor = BartrInk
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onSubmitRating(selectedStars, notesText) },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("submit_rating_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BartrBlue,
                contentColor = Color.White
            )
        ) {
            Text("Submit rating", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}
