package com.example.foroom.data.model.response

import com.example.foroom.data.model.ChatEntity

data class ChatsResponseEntity(
    val result: List<ChatEntity>,
    val hasNext: Boolean
)