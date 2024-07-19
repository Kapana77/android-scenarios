package com.example.foroom.domain.model

data class ChatsResponse(
    val chats: List<Chat>,
    val hasNext: Boolean
)