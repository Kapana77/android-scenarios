package com.example.design_system.components.input

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import androidx.core.widget.addTextChangedListener
import com.example.design_system.R
import com.example.design_system.components.empty.EmptyView
import com.example.design_system.components.input.validator.InputValidation
import com.example.design_system.components.input.validator.InputValidator
import com.example.design_system.databinding.LayoutForoomInputBinding
import com.example.shared.extension.INVALID_VALUE
import com.example.shared.extension.checkValue
import com.example.shared.extension.hide
import com.example.shared.extension.ifValidValue
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
    val text get() = binding.inputEditText.text.toString()
    var shouldClearDescriptionOnTextChange = false

    var startView: View? = null
        set(value) {
            handleNewStartView(value)
            field = value
        }

    var endView: View? = null
        set(value) {
            handleNewEndView(value)
            field = value
        }

    init {
        resources.obtainAttributes(attrs, R.styleable.Input).apply {
            descriptionType =
                DescriptionType.fromValue(getInt(R.styleable.Input_descriptionType, -1))
            description = getString(R.styleable.Input_description)

            getDrawable(R.styleable.Input_inputBackground)?.let { drawable ->
                binding.contentLinearLayout.background = drawable
            }

            getColor(R.styleable.Input_inputBackgroundTint, INVALID_VALUE).ifValidValue { value ->
                binding.contentLinearLayout.background.setTint(value)
            }

            with(editText) {
                hint = getString(R.styleable.Input_hint)
                inputType = getInt(R.styleable.Input_android_inputType, InputType.TYPE_CLASS_TEXT)

                val start = getDimensionPixelSize(
                    R.styleable.Input_inputPaddingStart,
                    INVALID_VALUE
                )

                val end = getDimensionPixelSize(
                    R.styleable.Input_inputPaddingEnd,
                    INVALID_VALUE
                )

                this.updateLayoutParams<MarginLayoutParams> {
                    leftMargin = checkValue(start, marginStart)
                    rightMargin = checkValue(end, marginEnd)
                }

                val drawableTint = getColor(R.styleable.Input_drawableTint, INVALID_VALUE)

                setCompoundDrawablesRelativeWithIntrinsicBounds(
                    getDrawable(R.styleable.Input_android_drawableStart).apply {
                        drawableTint.ifValidValue { this?.setTint(drawableTint) }
                    },
                    null,
                    getDrawable(R.styleable.Input_android_drawableEnd).apply {
                        drawableTint.ifValidValue { this?.setTint(drawableTint) }
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

    override fun onFinishInflate() {
        super.onFinishInflate()

        val views = children.toMutableList()
        views.removeFirst()

        views.firstOrNull()?.let { v ->
            removeView(v)
            startView = v
            views.removeFirst()
        }

        views.firstOrNull()?.let { v ->
            removeView(v)
            endView = v
            views.removeFirst()
        }
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

    fun addValidations(vararg validations: InputValidation) {
        validations.forEach(::addValidation)
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

    private fun handleNewStartView(view: View?) {
        if (startView != null) {
            binding.contentLinearLayout.removeViewAt(START_VIEW_INDEX)
        }

        if (view != null && view !is EmptyView) {
            binding.contentLinearLayout.addView(view, START_VIEW_INDEX)
        }
    }

    private fun handleNewEndView(view: View?) {
        if (endView != null) {
            binding.contentLinearLayout.removeViewAt(END_VIEW_INDEX)
        }

        if (view != null && view !is EmptyView) {
            binding.contentLinearLayout.addView(view)
        }
    }

    companion object {
        private const val ZERO_PADDING = 0

        private const val START_VIEW_INDEX = 0
        private const val END_VIEW_INDEX = 2
    }

    enum class DescriptionType(val value: Int) {
        INFO(1),
        ERROR(2);

        companion object {
            fun fromValue(value: Int) = entries.find { entry -> entry.value == value } ?: INFO
        }
    }
}