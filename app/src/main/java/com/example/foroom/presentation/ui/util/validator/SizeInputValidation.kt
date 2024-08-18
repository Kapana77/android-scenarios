package com.example.foroom.presentation.ui.util.validator

import com.example.design_system.components.input.validator.InputValidation

class SizeInputValidation(
    private val size: Int, private val errorMessage: String
) : InputValidation {
    override val message: String
        get() = errorMessage

    override fun isValid(text: String): Boolean {
        return text.length >= size
    }
}