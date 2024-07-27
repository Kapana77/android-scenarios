package com.example.foroom.domain.model

import java.time.LocalDateTime

data class Message(
    val id: Int,
    val senderUserName: String,
    val sendDate: LocalDateTime,
    val message: String,
    val userImage: String,
    val isCurrentUser: Boolean
)
