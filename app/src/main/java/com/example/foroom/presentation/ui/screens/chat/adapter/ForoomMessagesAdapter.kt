package com.example.foroom.presentation.ui.screens.chat.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.design_system.components.message.ForoomMessageView
import com.example.foroom.databinding.ItemForoomMessageBinding
import com.example.foroom.domain.model.Message
import com.example.shared.extension.screenWidthOf

class ForoomMessagesAdapter :
    ListAdapter<Message, ForoomMessagesAdapter.MessageViewHolder>(MessageDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].isCurrentUser) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding =
            ItemForoomMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return if (viewType == VIEW_TYPE_SENT) SentMessageViewHolder(binding) else ReceivedMessageViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class SentMessageViewHolder(private val binding: ItemForoomMessageBinding) :
        MessageViewHolder(binding) {
        override fun onBind(message: Message) {
            with(binding.messageView) {
                updateLayoutParams<FrameLayout.LayoutParams> {
                    gravity = Gravity.END
                    leftMargin = context.screenWidthOf(MESSAGE_VIEW_MARGIN_PERCENT).toInt()
                }
                setUp(message.message, message.senderUserName, message.sendDate)

                type = ForoomMessageView.Type.SENT
            }
        }

    }

    class ReceivedMessageViewHolder(private val binding: ItemForoomMessageBinding) :
        MessageViewHolder(binding) {
        override fun onBind(message: Message) {
            with(binding.messageView) {
                type = ForoomMessageView.Type.RECEIVED
                updateLayoutParams<FrameLayout.LayoutParams> {
                    rightMargin = context.screenWidthOf(MESSAGE_VIEW_MARGIN_PERCENT).toInt()
                }
                setUp(message.userImage, message.message, message.senderUserName, message.sendDate)
            }
        }

    }

    abstract class MessageViewHolder(binding: ItemForoomMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun onBind(message: Message)
    }

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2

        private const val MESSAGE_VIEW_MARGIN_PERCENT = 0.2F
    }
}