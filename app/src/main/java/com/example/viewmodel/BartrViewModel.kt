package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.ChatMessage
import com.example.model.JobHistoryItem
import com.example.model.JobOffer
import com.example.model.Screen
import com.example.model.VendorProfile
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class BartrUiState(
    val screenStack: List<Screen> = listOf(Screen.SPLASH),
    val balanceHidden: Boolean = false,
    val availableBalance: Int = 48500,
    val todayEarnings: Int = 0,
    val todayJobs: Int = 0,
    val weekJobs: Int = 9,
    val weekTotal: String = "₦52,000",
    val incomingRequests: List<JobOffer> = initialOffers,
    val activeRequest: JobOffer? = null,
    val selectedFilter: String = "forYou", // "forYou", "all", "Nail Tech", "Mechanic"
    val vendorCategory: String = "Phone Repair",
    val activeJobStage: Int = 0, // 0: Arriving, 1: Arrived, 2: In Progress
    val countdownSeconds: Int = 600,
    val isRequestTakenBannerVisible: Boolean = false,
    val chatMessages: List<ChatMessage> = listOf(
        ChatMessage("m1", "Hi, how far are you?", isMe = false, time = "10:14 AM")
    ),
    val vendorProfile: VendorProfile = VendorProfile(),
    val jobHistory: List<JobHistoryItem> = initialHistory,
    val alertMessage: String? = null,
    val isDrawerOpen: Boolean = false
)

private val initialOffers = listOf(
    JobOffer(
        id = "r1",
        customer = "David O.",
        custInitial = "D",
        custMeta = "★★★★★ 4.8 · 14 past requests",
        text = "My phone screen cracked, need am fix today",
        category = "Phone Repair",
        urgency = "Urgent · Today",
        price = "₦5,000",
        priceValue = 5000,
        distance = "0.6km",
        address = "14 Market Road, Ikeja",
        iconType = "wrench",
        colorType = "blue",
        lat = 6.6045,
        lng = 3.3565,
        isTaken = false
    ),
    JobOffer(
        id = "r2",
        customer = "Grace A.",
        custInitial = "G",
        custMeta = "★★★★★ 4.6 · 6 past requests",
        text = "Charging port dey loose, phone no wan charge well",
        category = "Phone Repair",
        urgency = "Today",
        price = "₦4,500",
        priceValue = 4500,
        distance = "1.1km",
        address = "9 Allen Avenue, Ikeja",
        iconType = "wrench",
        colorType = "blue",
        lat = 6.5975,
        lng = 3.3480,
        isTaken = false
    ),
    JobOffer(
        id = "r3",
        customer = "Tunde B.",
        custInitial = "T",
        custMeta = "★★★★☆ 4.4 · 3 past requests",
        text = "Back glass don crack, I need am replaced this weekend",
        category = "Phone Repair",
        urgency = "This weekend",
        price = "₦6,000",
        priceValue = 6000,
        distance = "1.8km",
        address = "22 Opebi Road, Ikeja",
        iconType = "wrench",
        colorType = "blue",
        lat = 6.6070,
        lng = 3.3610,
        isTaken = false
    ),
    JobOffer(
        id = "r4",
        customer = "Amaka N.",
        custInitial = "A",
        custMeta = "★★★★★ 4.9 · 21 past requests",
        text = "Need a gel manicure done at home this evening",
        category = "Nail Tech",
        urgency = "Today",
        price = "₦6,500",
        priceValue = 6500,
        distance = "1.4km",
        address = "5 Toyin Street, Ikeja",
        iconType = "spa",
        colorType = "green",
        lat = 6.5990,
        lng = 3.3555,
        isTaken = false
    ),
    JobOffer(
        id = "r5",
        customer = "Segun K.",
        custInitial = "S",
        custMeta = "★★★★☆ 4.3 · 2 past requests",
        text = "Car making strange noise, need a diagnostic check",
        category = "Mechanic",
        urgency = "This week",
        price = "₦12,000",
        priceValue = 12000,
        distance = "2.1km",
        address = "40 Awolowo Road, Ikeja",
        iconType = "car",
        colorType = "yellow",
        lat = 6.6060,
        lng = 3.3455,
        isTaken = false
    )
)

private val initialHistory = listOf(
    JobHistoryItem("h1", "Phone screen repair", "David O.", "Today", "Sept 2026", "Completed", "₦5,000", true),
    JobHistoryItem("h2", "Battery replacement", "Grace A.", "Yesterday", "Sept 2026", "Completed", "₦4,500", true),
    JobHistoryItem("h3", "Charging port fix", "Tunde B.", "3 days ago", "Aug 2026", "Completed", "₦6,000", true),
    JobHistoryItem("h4", "Water damage repair", "Amaka N.", "Last month", "Aug 2026", "Cancelled", "₦0", false)
)

class BartrViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BartrUiState())
    val uiState: StateFlow<BartrUiState> = _uiState.asStateFlow()

    private var countdownJob: Job? = null

    init {
        // Auto-transition from Splash / Loading screen to Home after 2.2 seconds
        viewModelScope.launch {
            delay(2200)
            if (_uiState.value.screenStack.size == 1 && _uiState.value.screenStack.first() == Screen.SPLASH) {
                _uiState.value = _uiState.value.copy(screenStack = listOf(Screen.HOME))
            }
        }

        // Demo simulation: simulate another vendor grabbing r2 shortly after load
        viewModelScope.launch {
            delay(15000)
            markOfferTaken("r2")
        }
    }

    val currentScreen: Screen
        get() = _uiState.value.screenStack.lastOrNull() ?: Screen.HOME

    fun previewLoadingScreen() {
        navigateTo(Screen.SPLASH)
        viewModelScope.launch {
            delay(2200)
            if (currentScreen == Screen.SPLASH) {
                goBack()
            }
        }
    }

    fun navigateTo(screen: Screen) {
        _uiState.value = _uiState.value.copy(
            screenStack = _uiState.value.screenStack + screen,
            isDrawerOpen = false
        )
    }

    fun goBack() {
        val stack = _uiState.value.screenStack
        if (stack.size > 1) {
            countdownJob?.cancel()
            _uiState.value = _uiState.value.copy(
                screenStack = stack.dropLast(1),
                isRequestTakenBannerVisible = false
            )
        }
    }

    fun resetToHome() {
        countdownJob?.cancel()
        _uiState.value = _uiState.value.copy(
            screenStack = listOf(Screen.HOME),
            isDrawerOpen = false,
            isRequestTakenBannerVisible = false
        )
    }

    fun setDrawerOpen(open: Boolean) {
        _uiState.value = _uiState.value.copy(isDrawerOpen = open)
    }

    fun toggleBalanceVisibility() {
        _uiState.value = _uiState.value.copy(balanceHidden = !_uiState.value.balanceHidden)
    }

    fun setFilter(filter: String) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
    }

    fun openRequestDetail(requestId: String) {
        val offer = _uiState.value.incomingRequests.find { it.id == requestId } ?: return
        if (offer.isTaken) {
            showAlert("This job has already been taken by another vendor.")
            return
        }

        _uiState.value = _uiState.value.copy(
            activeRequest = offer,
            countdownSeconds = 600,
            isRequestTakenBannerVisible = false
        )
        navigateTo(Screen.REQUEST_DETAIL)
        startCountdown(requestId)
    }

    private fun startCountdown(requestId: String) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            var seconds = 600
            while (seconds > 0) {
                delay(1000)
                seconds--
                _uiState.value = _uiState.value.copy(countdownSeconds = seconds)
            }
            markOfferTaken(requestId)
            _uiState.value = _uiState.value.copy(isRequestTakenBannerVisible = true)
        }
    }

    fun declineRequest() {
        countdownJob?.cancel()
        goBack()
    }

    fun acceptRequest() {
        countdownJob?.cancel()
        val request = _uiState.value.activeRequest ?: return
        val updatedOffers = _uiState.value.incomingRequests.filter { it.id != request.id }

        _uiState.value = _uiState.value.copy(
            incomingRequests = updatedOffers,
            activeJobStage = 0,
            chatMessages = listOf(
                ChatMessage("m1", "Hi, how far are you?", isMe = false, time = "Just now")
            )
        )
        navigateTo(Screen.ACTIVE_JOB)
    }

    fun markOfferTaken(requestId: String) {
        val updated = _uiState.value.incomingRequests.map {
            if (it.id == requestId) it.copy(isTaken = true) else it
        }
        val isCurrentlyViewing = _uiState.value.activeRequest?.id == requestId && currentScreen == Screen.REQUEST_DETAIL
        _uiState.value = _uiState.value.copy(
            incomingRequests = updated,
            isRequestTakenBannerVisible = if (isCurrentlyViewing) true else _uiState.value.isRequestTakenBannerVisible
        )
    }

    fun advanceJobStage() {
        val currentStage = _uiState.value.activeJobStage
        if (currentStage == 0) {
            _uiState.value = _uiState.value.copy(activeJobStage = 1)
        } else if (currentStage == 1) {
            _uiState.value = _uiState.value.copy(activeJobStage = 2)
        } else {
            navigateTo(Screen.RATING)
        }
    }

    fun cancelJob() {
        _uiState.value = _uiState.value.copy(activeRequest = null, activeJobStage = 0)
        resetToHome()
        showAlert("Job cancelled. The customer has been notified.")
    }

    fun sendChatMessage(text: String) {
        if (text.isBlank()) return
        val newMsg = ChatMessage(
            id = "m_${System.currentTimeMillis()}",
            text = text.trim(),
            isMe = true,
            time = "Just now"
        )
        _uiState.value = _uiState.value.copy(
            chatMessages = _uiState.value.chatMessages + newMsg
        )

        // Customer automated realistic reply
        viewModelScope.launch {
            delay(1200)
            val replies = listOf(
                "Alright, no problem! Waiting for you.",
                "Okay perfect, please take your time.",
                "Noted! Let me know as soon as you reach the gate."
            )
            val customerReply = ChatMessage(
                id = "m_${System.currentTimeMillis()}",
                text = replies.random(),
                isMe = false,
                time = "Just now"
            )
            _uiState.value = _uiState.value.copy(
                chatMessages = _uiState.value.chatMessages + customerReply
            )
        }
    }

    fun submitRating(stars: Int, feedback: String = "") {
        val request = _uiState.value.activeRequest
        val earned = request?.priceValue ?: 0

        val newHistoryItem = JobHistoryItem(
            id = "h_${System.currentTimeMillis()}",
            title = request?.text ?: "Service repair",
            customer = request?.customer ?: "Customer",
            dateOrMonth = "Today",
            monthSection = "Sept 2026",
            status = "Completed",
            price = request?.price ?: "₦$earned",
            isPositive = true
        )

        _uiState.value = _uiState.value.copy(
            todayEarnings = _uiState.value.todayEarnings + earned,
            todayJobs = _uiState.value.todayJobs + 1,
            availableBalance = _uiState.value.availableBalance + earned,
            jobHistory = listOf(newHistoryItem) + _uiState.value.jobHistory,
            activeRequest = null,
            activeJobStage = 0
        )
        resetToHome()
        showAlert("Job complete! ₦$earned added to your balance.")
    }

    fun updateProfile(
        businessName: String,
        phone: String,
        categories: List<String>,
        minPrice: String,
        maxPrice: String,
        about: String
    ) {
        _uiState.value = _uiState.value.copy(
            vendorProfile = _uiState.value.vendorProfile.copy(
                businessName = businessName,
                phone = phone,
                categories = categories,
                minPrice = minPrice,
                maxPrice = maxPrice,
                about = about
            )
        )
        goBack()
        showAlert("Profile updated successfully!")
    }

    fun showAlert(message: String) {
        _uiState.value = _uiState.value.copy(alertMessage = message)
    }

    fun clearAlert() {
        _uiState.value = _uiState.value.copy(alertMessage = null)
    }
}
