package com.example.borowallet.model

enum class NotificationType {
    ALERT,
    TRANSACTION,
    REWARD,
    SECURITY
}

data class NotificationItem(
    val id: String,
    val title: String,
    val description: String,
    val timeAgo: String,
    val isRead: Boolean = false,
    val type: NotificationType = NotificationType.ALERT
)
