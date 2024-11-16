package com.example.foroom.presentation.ui.screens.chat.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import com.example.design_system.components.message.ForoomMessageView
import com.example.design_system.R
import com.alternator.foroom.databinding.ItemForoomMessageBinding
import com.example.foroom.presentation.ui.model.MessageUI
import com.example.foroom.presentation.ui.util.adapter.ForoomLoadingListAdapter
import com.example.shared.extension.screenWidthOf
import com.example.shared.util.recyclerview.RecyclerViewEndReachListener

class ForoomMessagesAdapter(
    onLoadMore: () -> Unit,
    onErrorRefresh: () -> Unit
) : ForoomLoadingListAdapter<MessageUI>(
    onLoadMore,
    onErrorRefresh,
    RecyclerViewEndReachListener.Type.TOP
) {

    override fun getDataItemViewType(
        item: LoadingListItemType.DataItem<MessageUI>,
        position: Int
    ): Int {
        return if (item.data.isCurrentUser) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateDataItemViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoadingListDataViewHolder<MessageUI> {
        val binding =
            ItemForoomMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return if (viewType == VIEW_TYPE_SENT) SentMessageViewHolder(binding) else ReceivedMessageViewHolder(
            binding
        )
    }

    class SentMessageViewHolder(private val binding: ItemForoomMessageBinding) :
        MessageViewHolder(binding) {
        override fun onBind(item: MessageUI, position: Int) {
            with(binding.messageView) {
                updateLayoutParams<FrameLayout.LayoutParams> {
                    gravity = Gravity.END
                    leftMargin = context.screenWidthOf(MESSAGE_VIEW_MARGIN_PERCENT).toInt()
                    bottomMargin = resources.getDimensionPixelSize(
                        if (item.isMerged) R.dimen.spacing_2
                        else R.dimen.spacing_12
                    )
                }

                setUp(item.text, item.senderName, item.sendDate)

                messageType = ForoomMessageView.MessageType.Sent(item.isMerged)
            }
        }

    }

    class ReceivedMessageViewHolder(private val binding: ItemForoomMessageBinding) :
        MessageViewHolder(binding) {
        override fun onBind(item: MessageUI, position: Int) {
            with(binding.messageView) {
                messageType = ForoomMessageView.MessageType.Received(item.isMerged)

                updateLayoutParams<FrameLayout.LayoutParams> {
                    rightMargin = context.screenWidthOf(MESSAGE_VIEW_MARGIN_PERCENT).toInt()
                    bottomMargin = resources.getDimensionPixelSize(
                        if (item.isMerged) R.dimen.spacing_2
                        else R.dimen.spacing_12
                    )
                }

                setUp(item.senderAvatarUrl, item.text, item.senderName, item.sendDate)
            }
        }

    }

    abstract class MessageViewHolder(binding: ItemForoomMessageBinding) :
        LoadingListDataViewHolder<MessageUI>(binding)

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2

        private const val MESSAGE_VIEW_MARGIN_PERCENT = 0.2F
    }
}