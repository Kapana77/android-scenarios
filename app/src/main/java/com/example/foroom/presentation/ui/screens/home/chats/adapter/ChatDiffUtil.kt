package com.example.foroom.presentation.ui.screens.home.chats.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.foroom.domain.model.Chat

class ChatDiffUtil: DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }
}