package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Screen
import com.example.model.VendorProfile
import com.example.ui.theme.BartrBackdrop
import com.example.ui.theme.BartrBlue
import com.example.ui.theme.BartrBlueDark
import com.example.ui.theme.BartrBlueTint
import com.example.ui.theme.BartrDanger
import com.example.ui.theme.BartrInk
import com.example.ui.theme.BartrInkSoft
import com.example.viewmodel.BartrUiState

@Composable
fun ProfileScreen(
    state: BartrUiState,
    onBack: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onShowAlert: (String) -> Unit
) {
    val profile = state.vendorProfile

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
            horizontalArrangement = Arrangement.SpaceBetween,
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

            TextButton(
                onClick = { onNavigate(Screen.EDIT_PROFILE) },
                modifier = Modifier.testTag("edit_profile_button")
            ) {
                Text(
                    text = "Edit",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BartrBlue
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero avatar & business info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    color = BartrBlueTint
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = "Avatar",
                            tint = BartrBlue,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = profile.businessName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = BartrInk
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "★★★★★ ${profile.rating} · ${profile.reviewCount} reviews",
                    fontSize = 14.sp,
                    color = BartrBlue,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = profile.phone,
                    fontSize = 14.sp,
                    color = BartrInkSoft
                )
            }

            // Grey separator band
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(BartrBackdrop)
            )

            // Profile info body
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                Text(
                    text = "SERVICE CATEGORIES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = BartrInkSoft
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    profile.categories.forEach { category ->
                        Surface(
                            shape = CircleShape,
                            color = BartrBlueTint
                        ) {
                            Text(
                                text = category,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = BartrBlueDark,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "TYPICAL PRICE RANGE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = BartrInkSoft
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${profile.minPrice} – ${profile.maxPrice} per job",
                    fontSize = 15.sp,
                    color = BartrInk
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "ABOUT",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = BartrInkSoft
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = profile.about,
                    fontSize = 15.sp,
                    color = BartrInk,
                    lineHeight = 22.sp
                )
            }

            // Grey separator band
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(BartrBackdrop)
            )

            // Danger actions
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onShowAlert("Logged out (prototype only).") }
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Log out",
                        tint = BartrDanger,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Log out",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = BartrDanger
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onShowAlert("Account deletion isn't available in this prototype.") }
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete account",
                        tint = BartrDanger,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Delete account",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = BartrDanger
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun EditProfileScreen(
    currentProfile: VendorProfile,
    onCancel: () -> Unit,
    onSave: (String, String, List<String>, String, String, String) -> Unit,
    onShowAlert: (String) -> Unit
) {
    var businessName by remember { mutableStateOf(currentProfile.businessName) }
    var phone by remember { mutableStateOf(currentProfile.phone) }
    var categoriesText by remember { mutableStateOf(currentProfile.categories.joinToString(", ")) }
    var minPrice by remember { mutableStateOf(currentProfile.minPrice) }
    var maxPrice by remember { mutableStateOf(currentProfile.maxPrice) }
    var aboutText by remember { mutableStateOf(currentProfile.about) }

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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onCancel) {
                Text(
                    text = "Cancel",
                    color = BartrBlue,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = "Edit profile",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = BartrInk
            )

            Spacer(modifier = Modifier.width(60.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // Avatar with edit badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(BartrBlueTint)
                        .clickable { onShowAlert("Photo upload is coming soon!") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = "Avatar",
                        tint = BartrBlue,
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.Center)
                    )

                    Surface(
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape),
                        color = BartrBlue
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit photo",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }

            // Business name
            ProfileFormField(
                label = "BUSINESS NAME",
                value = businessName,
                onValueChange = { businessName = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Phone number
            ProfileFormField(
                label = "PHONE NUMBER",
                value = phone,
                onValueChange = { phone = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Categories
            ProfileFormField(
                label = "SERVICE CATEGORY",
                value = categoriesText,
                onValueChange = { categoriesText = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Price pair
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileFormField(
                    label = "MIN PRICE",
                    value = minPrice,
                    onValueChange = { minPrice = it },
                    modifier = Modifier.weight(1f)
                )
                ProfileFormField(
                    label = "MAX PRICE",
                    value = maxPrice,
                    onValueChange = { maxPrice = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // About
            ProfileFormField(
                label = "ABOUT YOUR BUSINESS",
                value = aboutText,
                onValueChange = { aboutText = it },
                singleLine = false,
                minLines = 3
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val cats = categoriesText.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    onSave(
                        businessName,
                        phone,
                        if (cats.isEmpty()) listOf("Phone Repair") else cats,
                        minPrice,
                        maxPrice,
                        aboutText
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("save_profile_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BartrBlue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Save changes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun ProfileFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp)),
        color = BartrBackdrop
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = BartrInkSoft,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                minLines = minLines,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = BartrInk,
                    unfocusedTextColor = BartrInk,
                    cursorColor = BartrBlue
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
