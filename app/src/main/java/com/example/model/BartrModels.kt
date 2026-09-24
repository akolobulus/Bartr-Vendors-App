package com.example.model

enum class Screen {
    SPLASH,
    HOME,
    JOB_OFFERS,
    REQUEST_DETAIL,
    ACTIVE_JOB,
    CHAT,
    RATING,
    EARNINGS,
    MY_JOBS,
    GET_HELP,
    FAQ_JOBS,
    FAQ_PAYOUTS,
    ABOUT,
    PROFILE,
    EDIT_PROFILE
}

data class JobOffer(
    val id: String,
    val customer: String,
    val custInitial: String,
    val custMeta: String,
    val text: String,
    val category: String,
    val urgency: String,
    val price: String,
    val priceValue: Int,
    val distance: String,
    val address: String,
    val iconType: String, // "wrench", "spa", "car"
    val colorType: String, // "blue", "green", "yellow"
    val lat: Double,
    val lng: Double,
    val isTaken: Boolean = false
)

data class VendorProfile(
    val businessName: String = "Chuka's Repairs",
    val phone: String = "+234 704 200 1836",
    val rating: String = "4.9",
    val reviewCount: Int = 128,
    val categories: List<String> = listOf("Phone Repair", "Tablet Repair"),
    val minPrice: String = "₦4,500",
    val maxPrice: String = "₦6,000",
    val about: String = "Specialists in screen and battery replacement, with same-day turnaround for most repairs. Based in Ikeja, serving a 3km radius."
)

data class JobHistoryItem(
    val id: String,
    val title: String,
    val customer: String,
    val dateOrMonth: String,
    val monthSection: String,
    val status: String, // "Completed", "Cancelled", "Today", etc.
    val price: String,
    val isPositive: Boolean = true
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isMe: Boolean,
    val time: String = "Just now"
)
