package com.example.foroom.presentation.ui.screens.home.chats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.foroom.databinding.ItemForoomChatBinding
import com.example.foroom.domain.model.Chat
import com.example.foroom.presentation.ui.util.adapter.ForoomLoadingListAdapter
import com.example.shared.extension.isEven

class ForoomChatsAdapter(
    onLoadMore: ()-> Unit
): ForoomLoadingListAdapter<Chat>(onLoadMore) {
    override fun onCreateDataItemViewHolder(
        parent: ViewGroup, viewType: Int
    ): LoadingListDataViewHolder<Chat> {
        return ViewHolder(
            ItemForoomChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class ViewHolder(private val binding: ItemForoomChatBinding) :
        LoadingListDataViewHolder<Chat>(binding) {

        override fun onBind(item: Chat, position: Int) {
            val angle = if (position.isEven()) POSITIVE_ANGLE else NEGATIVE_ANGLE

            with(binding.root) {
                setChatTitle(item.id.toString())
                setAuthorName(item.creatorUsername)
                setImageUrl(item.emojiUrl)
                hideRemoveButton()
                rotation = angle
            }
        }
    }

    companion object {
        private const val POSITIVE_ANGLE = 6F
        private const val NEGATIVE_ANGLE = -6F
    }
}