package com.example.design_system.components.chat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.design_system.databinding.LayoutForoomChatHeaderBinding
import com.example.shared.extension.loadImageUrl

class ForoomChatHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding =
        LayoutForoomChatHeaderBinding.inflate(LayoutInflater.from(context), this, true)

    fun setChatImageUrl(url: String) {
        binding.chatImageView.image.loadImageUrl(url)
    }

    fun setOnCloseButtonClickListener(block: ()-> Unit) {
        binding.closeButton.setOnClickListener {
            block()
        }
    }

    fun setAuthorName(name: String) {
        binding.userNameTextView.text = name
    }

    fun setChatTitle(title: String) {
        binding.chatNameTextView.text = title
    }
}