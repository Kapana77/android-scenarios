package com.example.foroom.domain.model

import java.time.LocalDateTime

data class Message(
    val senderName: String,
    val senderAvatarUrl: String,
    val sendDate: LocalDateTime,
    val text: String,
    val senderUserId: String,
    val id: Int
)
