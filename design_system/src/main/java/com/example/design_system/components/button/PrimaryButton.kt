package com.example.design_system.components.button

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import com.example.design_system.R
import com.example.shared.extension.dpToPx
import com.example.shared.extension.getDrawableFromAttribute

class PrimaryButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {
    private var buttonType: ButtonType = ButtonType.GREEN
        set(value) {
            field = value
            setBackgroundColor(value)
        }

    init {
        setStyles()

        resources.obtainAttributes(attrs, R.styleable.PrimaryButton).apply {
            buttonType = ButtonType.fromValue(
                getInt(R.styleable.PrimaryButton_buttonType, ButtonType.GREEN.value)
            ) ?: ButtonType.GREEN
        }.recycle()
    }

    private fun setStyles() {
        background = AppCompatResources.getDrawable(context, R.drawable.background_capsule)
        gravity = Gravity.CENTER
        setTextAppearance(R.style.jostText1Regular)

        val padding = BUTTON_PADDING.dpToPx(context).toInt()
        setPadding(
            paddingLeft,
            if (paddingTop != ZERO_PADDING) paddingTop else padding,
            paddingRight,
            if (paddingBottom != ZERO_PADDING) paddingBottom else padding
        )

        foreground =
            context.getDrawableFromAttribute(androidx.appcompat.R.attr.selectableItemBackground)
    }

    private fun setBackgroundColor(buttonType: ButtonType) {
        background.setTint(
            context.getColor(
                if (buttonType == ButtonType.GREEN) R.color.foroom_secondary_green
                else R.color.foroom_gray_dark
            )
        )
    }

    companion object {
        private const val BUTTON_PADDING = 16
        private const val ZERO_PADDING = 0
    }

    enum class ButtonType(val value: Int) {
        GREEN(1),
        DARK_GRAY(2);

        companion object {
            fun fromValue(value: Int) = entries.find { type -> type.value == value }
        }
    }
}