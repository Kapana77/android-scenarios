package com.example.foroom.domain.model.response

import com.example.foroom.domain.model.Message

data class MessageHistoryResponse(
    val messages: List<Message>,
    val hasNext: Boolean
)