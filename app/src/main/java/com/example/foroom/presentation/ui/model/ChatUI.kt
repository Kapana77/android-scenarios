package com.example.foroom.presentation.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatUI(
    val id: Int,
    val name: String,
    val emojiUrl: String,
    val creatorUsername: String,
    val likeCount: Int
): Parcelable