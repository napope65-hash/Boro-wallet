package com.example.borowallet.model

data class UserProfile(
    val name: String,
    val email: String,
    val referralCode: String,
    val cashBalance: Double,
    val isBiometricsEnabled: Boolean = true,
    val preferredCurrency: String = "USD",
    val membershipTier: String = "Private Member",
    val isFdicInsured: Boolean = true,
    val friendsInvited: Int = 4,
    val totalRewardsEarnedBtc: Double = 100.0
)
