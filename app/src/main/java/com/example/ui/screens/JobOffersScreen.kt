package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.JobOffer
import com.example.ui.theme.BartrBackdrop
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrBlueTint
import com.example.ui.theme.BartrGreenTint
import com.example.ui.theme.BartrInk
import com.example.ui.theme.BartrInkSoft
import com.example.ui.theme.BartrStar
import com.example.ui.theme.BartrSuccess
import com.example.ui.theme.BartrYellowTint
import com.example.viewmodel.BartrUiState

@Composable
fun JobOffersScreen(
    state: BartrUiState,
    onBack: () -> Unit,
    onFilterChange: (String) -> Unit,
    onSelectOffer: (String) -> Unit,
    onShowAlert: (String) -> Unit
) {
    val filteredOffers = state.incomingRequests.filter { offer ->
        when (state.selectedFilter) {
            "forYou" -> offer.category == state.vendorCategory
            "all" -> true
            else -> offer.category == state.selectedFilter
        }
    }

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

        // Page title
        Text(
            text = "Job offers",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = BartrInk,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Horizontal filter chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                label = "For you",
                isSelected = state.selectedFilter == "forYou",
                onClick = { onFilterChange("forYou") }
            )
            FilterChip(
                label = "All categories",
                isSelected = state.selectedFilter == "all",
                onClick = { onFilterChange("all") }
            )
            FilterChip(
                label = "Nail Tech",
                isSelected = state.selectedFilter == "Nail Tech",
                onClick = { onFilterChange("Nail Tech") }
            )
            FilterChip(
                label = "Mechanic",
                isSelected = state.selectedFilter == "Mechanic",
                onClick = { onFilterChange("Mechanic") }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Offers list
        if (filteredOffers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No job offers in this category right now. Check back soon, or browse another category.",
                    fontSize = 14.sp,
                    color = BartrInkSoft,
                    lineHeight = 20.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredOffers, key = { it.id }) { offer ->
                    JobOfferCard(
                        offer = offer,
                        onClick = {
                            if (offer.isTaken) {
                                onShowAlert("This job has already been taken by another vendor.")
                            } else {
                                onSelectOffer(offer.id)
                            }
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .testTag("filter_$label"),
        shape = CircleShape,
        color = if (isSelected) BartrBlue else BartrBackdrop
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isSelected) Color.White else BartrInk,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)
        )
    }
}

@Composable
private fun JobOfferCard(
    offer: JobOffer,
    onClick: () -> Unit
) {
    val cardAlpha = if (offer.isTaken) 0.55f else 1f

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(cardAlpha)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("offer_card_${offer.id}"),
        shape = RoundedCornerShape(16.dp),
        color = BartrBackdrop
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            val (iconBg, iconColor, iconVector) = when (offer.colorType) {
                "green" -> Triple(BartrGreenTint, BartrSuccess, Icons.Default.Face)
                "yellow" -> Triple(BartrYellowTint, BartrStar, Icons.Default.DirectionsCar)
                else -> Triple(BartrBlueTint, BartrBlue, Icons.Default.Build)
            }

            Surface(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp)),
                color = iconBg
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = offer.category,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = offer.text,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BartrInk,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${offer.category} · ${offer.urgency}",
                    fontSize = 12.sp,
                    color = BartrInkSoft
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Right price or booked pill
            if (offer.isTaken) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFE4E7EE)
                ) {
                    Text(
                        text = "Booked",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BartrInkSoft,
                        modifier = Modifier.padding(horizontal = 11.dp, vertical = 5.dp)
                    )
                }
            } else {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = offer.price,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = BartrInk
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = offer.distance,
                        fontSize = 11.sp,
                        color = BartrInkSoft
                    )
                }
            }
        }
    }
}
