package com.example.foroom.domain.model.response

import com.example.foroom.domain.model.Chat

data class ChatsResponse(
    val chats: List<Chat>,
    val hasNext: Boolean
)