package com.example.foroom.data.model

data class MessageEntity(
    val username: String,
    val userId: String,
    val avatarUrl: String,
    val createdAt: String,
    val text: String,
    val id: Int
)