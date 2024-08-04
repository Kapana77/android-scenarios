package com.example.foroom.data.model.request

data class SendMessageRequestEntity(
    val userId: String,
    val chatId: Int,
    val text: String
)