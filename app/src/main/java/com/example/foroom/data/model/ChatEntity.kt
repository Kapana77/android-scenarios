package com.example.foroom.data.model

data class ChatEntity(
    val name: String,
    val emojiUrl: String,
    val creatorUsername: String,
    val likeCount: Int,
    val id: Long
)
