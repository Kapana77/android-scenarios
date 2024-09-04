package com.example.foroom.presentation.ui.model

import java.time.LocalDateTime

data class MessageUI(
    val senderName: String,
    val senderAvatarUrl: String,
    val sendDate: LocalDateTime,
    val text: String,
    val senderUserId: String,
    val id: Int,
    val isCurrentUser: Boolean,
    val isMerged: Boolean
)
