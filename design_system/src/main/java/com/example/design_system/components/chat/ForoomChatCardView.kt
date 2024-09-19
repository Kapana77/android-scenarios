package com.example.design_system.components.chat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import com.example.design_system.R
import com.example.design_system.components.shimmer.ForoomShimmerDrawableBuilder
import com.example.design_system.databinding.LayoutForoomChatBinding
import com.example.shared.extension.loadImageUrl
import com.example.shared.extension.onClick
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class ForoomChatCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding = LayoutForoomChatBinding.inflate(LayoutInflater.from(context), this, true)

    var onSendMessageButtonClick: (()-> Unit)? = null
    var onStarButtonClick: (()-> Unit)? = null
    var onRemoveButtonClick: (()-> Unit)? = null

    private var calculatedRotationMargin = false

    init {
        binding.sendMessageButton.onClick {
            onSendMessageButtonClick?.invoke()
        }

        binding.starButton.onClick {
            onStarButtonClick?.invoke()
        }

        binding.removeButton.onClick {
            onRemoveButtonClick?.invoke()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        post { setRotationalVerticalMargins() }
    }

    private fun setRotationalVerticalMargins() {
        if (calculatedRotationMargin || height == 0 || width == 0) return

        val negativeRotation = -abs(rotation)
        val a = height / 2
        val b = width / 2
        val margin = (a*sin(negativeRotation)+b*(cos(negativeRotation)-1)).toInt()

        updateLayoutParams<MarginLayoutParams> {
            setMargins(
                marginLeft, margin + marginTop, marginRight, margin + marginBottom
            )
        }

        calculatedRotationMargin = true
    }

    fun setImageUrl(url: String) {
        binding.chatImageView.image.loadImageUrl(
            url,
            ForoomShimmerDrawableBuilder()
                .setHighlightColorRes(R.color.foroom_main_green)
                .setHighlightAlpha(0.15f)
                .build(context)
        )
    }

    fun setAuthorName(name: String) {
        binding.authorNameTextView.text = name
    }

    fun setChatTitle(title: String) {
        binding.chatTitleTextView.text = title
    }

    fun hideRemoveButton() {
        binding.removeButton.isVisible = false
    }

    fun showRemoveButton() {
        binding.removeButton.isVisible = true
    }

    fun setIsFavorite(isFavorite: Boolean) {
        binding.starButton.setColorFilter(
            context.getColor(
                if (isFavorite) R.color.foroom_main_green
                else R.color.foroom_gray_light
            )
        )
    }
}