package com.example.design_system.components.button

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.setPadding
import com.example.design_system.R
import com.example.shared.extension.dpToPx
import com.example.shared.extension.getDrawableFromAttribute

class SecondaryButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {
    init {
        setStyles()
    }

    private fun setStyles() {
        background = AppCompatResources.getDrawable(context, R.drawable.background_rounded_12dp)
        background.setTint(context.getColor(R.color.foroom_layer_background))
        gravity = Gravity.CENTER_VERTICAL

        setTextAppearance(R.style.jostText2Medium)

        val padding = BUTTON_PADDING.dpToPx(context).toInt()
        setPadding(padding)

        foreground =
            context.getDrawableFromAttribute(androidx.appcompat.R.attr.selectableItemBackground)
    }

    companion object {
        private const val BUTTON_PADDING = 12
    }
}