package com.example.design_system.components.message

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.example.design_system.R
import com.example.design_system.databinding.LayoutForoomMessageBinding
import com.example.shared.extension.loadImageUrl
import com.example.shared.util.formatter.SmartDateFormatter
import java.time.LocalDateTime
import java.util.Date

class ForoomMessageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding =
        LayoutForoomMessageBinding.inflate(LayoutInflater.from(context), this, true)

    var type: Type? = null
        set(value) {
            field = value
            handleTypeChange()
        }

    init {
        resources.obtainAttributes(attrs, R.styleable.ForoomMessageView).apply {
            type =
                Type.fromValue(getInt(R.styleable.ForoomMessageView_messageType, Type.SENT.value))
        }.recycle()
    }

    fun setUp(userImageUrl: String, message: String, userName: String, messageDate: LocalDateTime?) {
        binding.userImageView.image.loadImageUrl(userImageUrl)
        setUp(message, userName, messageDate)
    }

    fun setUp(message: String, userName: String, messageDate: LocalDateTime?) {
        binding.userNameTextView.text = userName
        binding.messageDateTextView.text = SmartDateFormatter.formatDate(messageDate)
        binding.messageTextView.text = message
    }

    private fun handleTypeChange() {
        val isReceived = type == Type.RECEIVED

        binding.backgroundView.background = ContextCompat.getDrawable(
            context,
            if (isReceived) R.drawable.background_message_received else R.drawable.background_message_sent
        )
        binding.userImageView.isVisible = isReceived
    }

    enum class Type(val value: Int) {
        SENT(1),
        RECEIVED(2);

        companion object {
            fun fromValue(value: Int): Type? {
                return entries.find { type -> type.value == value }
            }
        }
    }
}