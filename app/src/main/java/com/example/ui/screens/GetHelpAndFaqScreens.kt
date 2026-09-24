package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Screen
import com.example.ui.theme.BartrBackdrop
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrInk
import com.example.ui.theme.BartrInkSoft

@Composable
fun GetHelpScreen(
    onBack: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onShowAlert: (String) -> Unit
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
            text = "Get help",
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
            HelpNavCard(
                icon = Icons.Default.Work,
                title = "Getting and managing jobs",
                onClick = { onNavigate(Screen.FAQ_JOBS) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            HelpNavCard(
                icon = Icons.Default.Receipt,
                title = "Payments and payouts",
                onClick = { onNavigate(Screen.FAQ_PAYOUTS) }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "STILL NEED HELP?",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp,
                color = BartrInkSoft
            )

            Spacer(modifier = Modifier.height(12.dp))

            HelpNavCard(
                icon = Icons.Default.Forum,
                title = "Chat with support",
                onClick = { onShowAlert("Live chat is coming soon!") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            HelpNavCard(
                icon = Icons.Default.Phone,
                title = "Call support",
                onClick = { onShowAlert("Call support: +234 800 000 0000") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            HelpNavCard(
                icon = Icons.Default.Email,
                title = "Email us",
                onClick = { onShowAlert("Email: vendors@bartr.app") }
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun HelpNavCard(
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
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = BartrInk,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Navigate",
                tint = BartrInkSoft,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun FaqJobsScreen(onBack: () -> Unit) {
    val faqs = listOf(
        "How requests are matched to me" to "Requests are automatically matched based on your chosen service category, current distance (usually within 3km), and your vendor rating.",
        "Why a request expired before I responded" to "To provide customers fast turnarounds, requests have an initial response window before becoming available to other top-rated vendors in your area.",
        "How to set your service categories" to "You can update your active service categories any time in your vendor Profile settings under Edit Profile.",
        "Improving your ranking in requests" to "Maintain a high completion rate, respond promptly to offers, and deliver 5-star quality repairs to boost your priority rank.",
        "Cancelling an accepted job" to "You can cancel an active job from the job sheet. Please only cancel when strictly necessary to keep your vendor score healthy.",
        "Why a job shows as \"Booked\"" to "A job shows as Booked when another nearby vendor accepted the offer while you were viewing the offers list."
    )

    FaqScreenTemplate(
        title = "Getting and managing jobs",
        faqs = faqs,
        onBack = onBack
    )
}

@Composable
fun FaqPayoutsScreen(onBack: () -> Unit) {
    val faqs = listOf(
        "How and when you get paid" to "For cash jobs, collect payment directly from the customer upon completion. Digital wallet earnings can be withdrawn to your Nigerian bank account.",
        "Withdrawing your balance" to "Tap Withdraw in the Earnings tab. Standard withdrawals settle directly to your verified commercial bank in 1 to 2 business days.",
        "How Bartr's commission works" to "Bartr charges a standard transparent 10% platform fee on completed jobs to cover support and platform infrastructure.",
        "A customer paid me outside the app" to "For cash transactions, mark the job complete after receiving cash. No additional steps are required.",
        "Disputing a job's final price" to "If additional parts or labor were required beyond the original estimate, contact support before closing out the job.",
        "Adding your bank account" to "Go to Profile > Payment details to link your NUBAN account number and verify your bank name."
    )

    FaqScreenTemplate(
        title = "Payments and payouts",
        faqs = faqs,
        onBack = onBack
    )
}

@Composable
private fun FaqScreenTemplate(
    title: String,
    faqs: List<Pair<String, String>>,
    onBack: () -> Unit
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
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = BartrInk,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            faqs.forEach { (question, answer) ->
                var expanded by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                        .padding(vertical = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = question,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = BartrInk,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expand",
                            tint = BartrInkSoft,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    AnimatedVisibility(visible = expanded) {
                        Text(
                            text = answer,
                            fontSize = 14.sp,
                            color = BartrInkSoft,
                            lineHeight = 20.sp,
                            modifier = Modifier.padding(top = 10.dp, end = 16.dp)
                        )
                    }
                }
                Divider(color = BartrInk.copy(alpha = 0.08f))
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
