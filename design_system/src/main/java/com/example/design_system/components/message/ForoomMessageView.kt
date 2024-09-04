package com.example.design_system.components.message

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.design_system.R
import com.example.design_system.databinding.LayoutForoomMessageBinding
import com.example.shared.extension.hide
import com.example.shared.extension.loadImageUrl
import com.example.shared.extension.show
import com.example.shared.util.formatter.SmartDateFormatter
import java.time.LocalDateTime

class ForoomMessageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding =
        LayoutForoomMessageBinding.inflate(LayoutInflater.from(context), this, true)

    var messageType: MessageType? = null
        set(value) {
            field = value
            handleTypeChange()
        }

    fun setUp(
        userImageUrl: String,
        message: String,
        userName: String,
        messageDate: LocalDateTime?
    ) {
        binding.userImageView.image.loadImageUrl(userImageUrl)
        setUp(message, userName, messageDate)
    }

    fun setUp(message: String, userName: String, messageDate: LocalDateTime?) {
        binding.userNameTextView.text = userName
        binding.messageDateTextView.text = SmartDateFormatter.formatDate(messageDate)
        binding.messageTextView.text = message
    }

    private fun handleTypeChange() {
        messageType?.let { type ->
            when {
                type.isMerged -> {
                    with(binding.contentLinearLayout) {
                        background =
                            ContextCompat.getDrawable(context, R.drawable.background_message_merged)
                        background.setTint(context.getColor(type.colorRes))
                    }

                    if (type is MessageType.Sent) {
                        binding.userImageView.hide()
                    } else {
                        binding.userImageView.visibility = View.INVISIBLE
                    }
                }

                type is MessageType.Sent -> {
                    binding.contentLinearLayout.background =
                        ContextCompat.getDrawable(context, R.drawable.background_message_sent)
                    binding.userImageView.hide()
                }

                type is MessageType.Received -> {
                    binding.contentLinearLayout.background =
                        ContextCompat.getDrawable(context, R.drawable.background_message_received)
                    binding.userImageView.show()
                }
            }
        }
    }

    sealed class MessageType(open val isMerged: Boolean, val colorRes: Int) {
        data class Sent(override val isMerged: Boolean) :
            MessageType(isMerged, R.color.foroom_main_green)

        data class Received(override val isMerged: Boolean) :
            MessageType(isMerged, R.color.foroom_white_faded_tr_95)
    }
}