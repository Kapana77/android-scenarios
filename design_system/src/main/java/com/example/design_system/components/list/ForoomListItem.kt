package com.example.design_system.components.list

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.design_system.R
import com.example.design_system.databinding.LayoutForoomListItemBinding

class ForoomListItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding =
        LayoutForoomListItemBinding.inflate(LayoutInflater.from(context), this, true)

    var startImage: Drawable? = null
        set(value) {
            field = value
            binding.startImageView.setImageDrawable(value)
            binding.startImageView.isVisible = value != null
        }

    var text: String? = null
        set(value) {
            field = value
            binding.listItemTextView.text = value
        }

    var endImage: Drawable? = null
        set(value) {
            field = value
            binding.endImageView.setImageDrawable(value)
            binding.endImageView.isVisible = value != null
        }

    var hasBottomDivider = false
        set(value) {
            field = value

            background =
                if (hasBottomDivider)
                    ContextCompat.getDrawable(context, R.drawable.background_item_divider_bottom)
                else
                    null
        }

    init {
        resources.obtainAttributes(attrs, R.styleable.ForoomListItem).apply {
            startImage = getDrawable(R.styleable.ForoomListItem_imageStart)

            text = getString(R.styleable.ForoomListItem_title)

            endImage = getDrawable(R.styleable.ForoomListItem_imageEnd)

            hasBottomDivider = getBoolean(R.styleable.ForoomListItem_hasBottomDivider, false)
        }.recycle()
    }
}