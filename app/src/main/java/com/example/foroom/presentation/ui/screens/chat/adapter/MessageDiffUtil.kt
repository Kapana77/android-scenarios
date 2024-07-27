package com.example.foroom.presentation.ui.screens.chat.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.foroom.domain.model.Message

class MessageDiffUtil: DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }

}