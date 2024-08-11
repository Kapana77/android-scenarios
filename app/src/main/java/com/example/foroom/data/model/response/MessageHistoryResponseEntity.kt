package com.example.foroom.data.model.response

import com.example.foroom.data.model.MessageEntity

data class MessageHistoryResponseEntity(
    val result: List<MessageEntity>,
    val hasNext: Boolean
)