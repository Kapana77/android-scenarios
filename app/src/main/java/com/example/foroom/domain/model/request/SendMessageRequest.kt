package com.example.foroom.domain.model.request

data class SendMessageRequest(
    val userId: String,
    val chatId: Int,
    val text: String
)