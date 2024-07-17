package com.example.design_system.components.input

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.setPadding
import androidx.core.widget.addTextChangedListener
import com.example.design_system.R
import com.example.design_system.components.input.validator.InputValidation
import com.example.design_system.components.input.validator.InputValidator
import com.example.design_system.databinding.LayoutForoomInputBinding
import com.example.shared.extension.INVALID_RESOURCE
import com.example.shared.extension.hide
import com.example.shared.extension.ifValidResource
import com.example.shared.extension.show

class Input @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding = LayoutForoomInputBinding.inflate(LayoutInflater.from(context), this, true)
    private var descriptionType: DescriptionType
    private var description: String? = null
        set(value) {
            field = value
            updateDescription()
        }

    private val inputValidator = InputValidator()

    val editText get() = binding.inputEditText
    var shouldClearDescriptionOnTextChange = false

    init {
        resources.obtainAttributes(attrs, R.styleable.Input).apply {
            descriptionType =
                DescriptionType.fromValue(getInt(R.styleable.Input_descriptionType, -1))
            description = getString(R.styleable.Input_description)

            with(editText) {
                hint = getString(R.styleable.Input_hint)
                inputType = getInt(R.styleable.Input_android_inputType, InputType.TYPE_CLASS_TEXT)

                val drawableTint = getColor(R.styleable.Input_drawableTint, INVALID_RESOURCE)

                setCompoundDrawablesRelativeWithIntrinsicBounds(
                    getDrawable(R.styleable.Input_android_drawableStart).apply {
                        drawableTint.ifValidResource { this?.setTint(drawableTint) }
                    },
                    null,
                    getDrawable(R.styleable.Input_android_drawableEnd).apply {
                        drawableTint.ifValidResource { this?.setTint(drawableTint) }
                    },
                    null
                )

                compoundDrawablePadding =
                    getDimensionPixelSize(R.styleable.Input_android_drawablePadding, ZERO_PADDING)

                setPadding(
                    getDimensionPixelSize(
                        R.styleable.Input_contentPadding,
                        resources.getDimensionPixelSize(R.dimen.spacing_16)
                    )
                )

                setTextAppearance(
                    getResourceId(
                        R.styleable.Input_android_textAppearance,
                        R.style.jostText1Regular
                    )
                )

                setHintTextColor(context.getColor(R.color.foroom_white_faded_tr_20))
            }
        }.recycle()

        handleTextChange()
    }

    fun setInputDescription(
        description: String,
        descriptionType: DescriptionType,
        shouldClearDescriptionOnTextChange: Boolean = false
    ) {
        // Need to set this first because description will call updateDescription() with old type
        this.descriptionType = descriptionType
        this.description = description
        this.shouldClearDescriptionOnTextChange = shouldClearDescriptionOnTextChange
    }

    fun setInputDescription(
        descriptionRes: Int,
        descriptionType: DescriptionType,
        shouldClearDescriptionOnTextChange: Boolean = false
    ) {
        setInputDescription(
            context.getString(descriptionRes),
            descriptionType,
            shouldClearDescriptionOnTextChange
        )
    }

    fun clearInputDescription() {
        description = null
    }

    fun addValidation(validation: InputValidation) {
        inputValidator.addValidation(validation)
    }

    fun validate(): Boolean {
        return inputValidator.validate(this)
    }

    private fun updateDescription() = with(binding.descriptionTextView) {
        if (description == null) {
            hide()
            return
        } else show()

        setTextColor(
            context.getColor(
                if (descriptionType == DescriptionType.INFO) R.color.foroom_white_faded_tr_50
                else R.color.foroom_background_pink
            )
        )

        text = description
    }

    private fun handleTextChange() {
        binding.inputEditText.addTextChangedListener { _ ->
            if (shouldClearDescriptionOnTextChange) clearInputDescription()
        }
    }

    companion object {
        private const val ZERO_PADDING = 0
    }

    enum class DescriptionType(val value: Int) {
        INFO(1),
        ERROR(2);

        companion object {
            fun fromValue(value: Int) = entries.find { entry -> entry.value == value } ?: INFO
        }
    }
}