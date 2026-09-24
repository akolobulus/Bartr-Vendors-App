package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Screen
import com.example.ui.components.BartrMapCanvas
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrBlueDark
import com.example.ui.theme.BartrBlueTint
import com.example.ui.theme.BartrGreenTint
import com.example.ui.theme.BartrInk
import com.example.ui.theme.BartrInkSoft
import com.example.viewmodel.BartrUiState

@Composable
fun HomeScreen(
    state: BartrUiState,
    onOpenDrawer: () -> Unit,
    onCloseDrawer: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onOpenRequest: (String) -> Unit,
    onToggleBalance: () -> Unit,
    onShowAlert: (String) -> Unit
) {
    val openOffersCount = state.incomingRequests.count { !it.isTaken }
    val offersLabel = if (openOffersCount == 0) {
        "No job offers right now"
    } else {
        "$openOffersCount new job offer${if (openOffersCount == 1) "" else "s"}"
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 1. Map container (top ~38% of viewport)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.42f)
            ) {
                BartrMapCanvas(
                    requests = state.incomingRequests,
                    onPinClick = onOpenRequest
                )

                // Hamburger Menu Floating Button
                Surface(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(start = 20.dp, top = 14.dp)
                        .size(46.dp)
                        .shadow(10.dp, CircleShape)
                        .clip(CircleShape)
                        .testTag("menu_button"),
                    color = Color.White
                ) {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = BartrInk,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            // 2. Sheet Card (bottom overlay)
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
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    // Today earnings mini bar
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp)),
                        color = BartrGreenTint
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val displayAmount = if (state.balanceHidden) "₦••••" else "₦${state.todayEarnings}"
                            Text(
                                text = "Today · $displayAmount · ${state.todayJobs} jobs",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BartrInk
                            )
                            IconButton(
                                onClick = onToggleBalance,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = if (state.balanceHidden) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Toggle balance visibility",
                                    tint = BartrInkSoft,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "JOB OFFERS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = BartrInkSoft
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Offers Teaser Banner Card
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onNavigate(Screen.JOB_OFFERS) }
                            .testTag("offers_teaser"),
                        color = BartrBlueTint
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                color = Color.White
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Work,
                                        contentDescription = "Offers",
                                        tint = BartrBlue,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = offersLabel,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = BartrInk
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Tap to view and filter by category",
                                    fontSize = 12.sp,
                                    color = BartrInkSoft
                                )
                            }

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                contentDescription = "View offers",
                                tint = BartrInkSoft,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // Side Navigation Drawer
        AnimatedVisibility(
            visible = state.isDrawerOpen,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x5A0A2E65))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onCloseDrawer() }
            )
        }

        AnimatedVisibility(
            visible = state.isDrawerOpen,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .statusBarsPadding()
                    .padding(vertical = 12.dp, horizontal = 18.dp),
                shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                color = Color.White,
                shadowElevation = 16.dp
            ) {
                Column(modifier = Modifier.fillMaxSize().padding(top = 10.dp)) {
                    // Profile button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                onCloseDrawer()
                                onNavigate(Screen.PROFILE)
                            }
                            .padding(vertical = 12.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            color = BartrBlueTint
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Build,
                                    contentDescription = "Profile",
                                    tint = BartrBlue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = state.vendorProfile.businessName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = BartrInk
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "★★★★★ ${state.vendorProfile.rating} · Phone Repair",
                                fontSize = 12.sp,
                                color = BartrBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = BartrInk.copy(alpha = 0.08f))
                    Spacer(modifier = Modifier.height(10.dp))

                    // Menu list
                    DrawerMenuItem(
                        icon = Icons.Default.AccountBalanceWallet,
                        title = "Earnings",
                        onClick = {
                            onCloseDrawer()
                            onNavigate(Screen.EARNINGS)
                        }
                    )
                    DrawerMenuItem(
                        icon = Icons.Default.History,
                        title = "My jobs",
                        onClick = {
                            onCloseDrawer()
                            onNavigate(Screen.MY_JOBS)
                        }
                    )
                    DrawerMenuItem(
                        icon = Icons.Default.HelpOutline,
                        title = "Get help",
                        onClick = {
                            onCloseDrawer()
                            onNavigate(Screen.GET_HELP)
                        }
                    )
                    DrawerMenuItem(
                        icon = Icons.Default.Info,
                        title = "About",
                        onClick = {
                            onCloseDrawer()
                            onNavigate(Screen.ABOUT)
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Bottom CTA: Switch to customer mode
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                onShowAlert("Switching to the customer app is coming soon!")
                            }
                            .testTag("switch_mode_cta"),
                        color = BartrBlue
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "Switch to customer mode",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "Post a request of your own",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun DrawerMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 14.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = BartrInk,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = BartrInk
        )
    }
}
