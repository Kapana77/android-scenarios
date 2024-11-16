package com.example.foroom.presentation.ui.util.validator

import com.example.design_system.components.input.validator.InputValidation
import com.alternator.foroom.R

object BlankInputValidation: InputValidation {
    override val messageRes: Int get() = R.string.blank_input_error

    override fun isValid(text: String): Boolean {
        return text.isNotBlank()
    }
}