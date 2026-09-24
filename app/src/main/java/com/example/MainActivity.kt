package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Screen
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.ActiveJobScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.EarningsScreen
import com.example.ui.screens.EditProfileScreen
import com.example.ui.screens.FaqJobsScreen
import com.example.ui.screens.FaqPayoutsScreen
import com.example.ui.screens.GetHelpScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.JobOffersScreen
import com.example.ui.screens.MyJobsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RatingScreen
import com.example.ui.screens.RequestDetailScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrInk
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.BartrViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: BartrViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                BartrApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun BartrApp(viewModel: BartrViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val currentScreen = uiState.screenStack.lastOrNull() ?: Screen.HOME
    val snackbarHostState = remember { SnackbarHostState() }

    // Android hardware / gesture back button handling
    BackHandler(enabled = uiState.screenStack.size > 1) {
        if (uiState.isDrawerOpen) {
            viewModel.setDrawerOpen(false)
        } else {
            viewModel.goBack()
        }
    }

    // Show alert messages if any
    uiState.alertMessage?.let { alert ->
        AlertDialog(
            onDismissRequest = { viewModel.clearAlert() },
            title = {
                Text(
                    text = "Notice",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = BartrInk
                )
            },
            text = {
                Text(
                    text = alert,
                    fontSize = 15.sp,
                    color = BartrInk
                )
            },
            confirmButton = {
                TextButton(onClick = { viewModel.clearAlert() }) {
                    Text("OK", color = BartrBlue, fontWeight = FontWeight.Bold)
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { screen ->
                when (screen) {
                    Screen.SPLASH -> SplashScreen(
                        onDismiss = { viewModel.resetToHome() }
                    )

                    Screen.HOME -> HomeScreen(
                        state = uiState,
                        onOpenDrawer = { viewModel.setDrawerOpen(true) },
                        onCloseDrawer = { viewModel.setDrawerOpen(false) },
                        onNavigate = { viewModel.navigateTo(it) },
                        onOpenRequest = { viewModel.openRequestDetail(it) },
                        onToggleBalance = { viewModel.toggleBalanceVisibility() },
                        onShowAlert = { viewModel.showAlert(it) }
                    )

                    Screen.JOB_OFFERS -> JobOffersScreen(
                        state = uiState,
                        onBack = { viewModel.goBack() },
                        onFilterChange = { viewModel.setFilter(it) },
                        onSelectOffer = { viewModel.openRequestDetail(it) },
                        onShowAlert = { viewModel.showAlert(it) }
                    )

                    Screen.REQUEST_DETAIL -> RequestDetailScreen(
                        state = uiState,
                        onDecline = { viewModel.declineRequest() },
                        onAccept = { viewModel.acceptRequest() },
                        onBack = { viewModel.goBack() }
                    )

                    Screen.ACTIVE_JOB -> ActiveJobScreen(
                        state = uiState,
                        onBack = { viewModel.goBack() },
                        onOpenChat = { viewModel.navigateTo(Screen.CHAT) },
                        onAdvanceStage = { viewModel.advanceJobStage() },
                        onCancelJob = { viewModel.cancelJob() },
                        onShowAlert = { viewModel.showAlert(it) }
                    )

                    Screen.CHAT -> ChatScreen(
                        state = uiState,
                        onBack = { viewModel.goBack() },
                        onSendMessage = { viewModel.sendChatMessage(it) }
                    )

                    Screen.RATING -> RatingScreen(
                        state = uiState,
                        onSubmitRating = { stars, notes -> viewModel.submitRating(stars, notes) }
                    )

                    Screen.EARNINGS -> EarningsScreen(
                        state = uiState,
                        onBack = { viewModel.goBack() },
                        onToggleBalance = { viewModel.toggleBalanceVisibility() },
                        onWithdraw = { viewModel.showAlert("Withdrawals process in 1 to 2 business days.") }
                    )

                    Screen.MY_JOBS -> MyJobsScreen(
                        state = uiState,
                        onBack = { viewModel.goBack() }
                    )

                    Screen.GET_HELP -> GetHelpScreen(
                        onBack = { viewModel.goBack() },
                        onNavigate = { viewModel.navigateTo(it) },
                        onShowAlert = { viewModel.showAlert(it) }
                    )

                    Screen.FAQ_JOBS -> FaqJobsScreen(
                        onBack = { viewModel.goBack() }
                    )

                    Screen.FAQ_PAYOUTS -> FaqPayoutsScreen(
                        onBack = { viewModel.goBack() }
                    )

                    Screen.ABOUT -> AboutScreen(
                        onBack = { viewModel.goBack() },
                        onShowAlert = { viewModel.showAlert(it) },
                        onPreviewLoadingScreen = { viewModel.previewLoadingScreen() }
                    )

                    Screen.PROFILE -> ProfileScreen(
                        state = uiState,
                        onBack = { viewModel.goBack() },
                        onNavigate = { viewModel.navigateTo(it) },
                        onShowAlert = { viewModel.showAlert(it) }
                    )

                    Screen.EDIT_PROFILE -> EditProfileScreen(
                        currentProfile = uiState.vendorProfile,
                        onCancel = { viewModel.goBack() },
                        onSave = { name, phone, cats, minP, maxP, about ->
                            viewModel.updateProfile(name, phone, cats, minP, maxP, about)
                        },
                        onShowAlert = { viewModel.showAlert(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}
