package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChatMessage
import com.example.ui.theme.BartrBackdrop
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrBlueTint
import com.example.ui.theme.BartrInk
import com.example.ui.theme.BartrInkSoft
import com.example.viewmodel.BartrUiState

@Composable
fun ChatScreen(
    state: BartrUiState,
    onBack: () -> Unit,
    onSendMessage: (String) -> Unit
) {
    val customerName = state.activeRequest?.customer ?: "Customer"
    val custInitial = state.activeRequest?.custInitial ?: "C"
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .imePadding()
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
                    .testTag("chat_back_button"),
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

            Spacer(modifier = Modifier.width(12.dp))

            Surface(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp)),
                color = BartrBlueTint
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = custInitial,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = BartrBlue
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = customerName,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = BartrInk
            )
        }

        Divider(color = BartrInk.copy(alpha = 0.08f))

        // Message body
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            items(state.chatMessages, key = { it.id }) { msg ->
                ChatBubble(message = msg)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        // Input row
        Divider(color = BartrInk.copy(alpha = 0.08f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("Type a message", fontSize = 14.sp, color = BartrInkSoft) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input"),
                shape = CircleShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BartrBlue,
                    unfocusedBorderColor = Color(0x200A2E65),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = BartrBackdrop,
                    focusedTextColor = BartrInk,
                    unfocusedTextColor = BartrInk
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(10.dp))

            Surface(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .testTag("chat_send_button"),
                color = BartrBlue
            ) {
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onSendMessage(inputText)
                            inputText = ""
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (message.isMe) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Surface(
            shape = if (message.isMe) {
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 4.dp)
            } else {
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 4.dp)
            },
            color = if (message.isMe) BartrBlue else BartrBackdrop,
            modifier = Modifier.fillMaxWidth(0.78f)
        ) {
            Text(
                text = message.text,
                fontSize = 14.sp,
                color = if (message.isMe) Color.White else BartrInk,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
    }
}
