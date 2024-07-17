package com.example.design_system.components.input.validator

import com.example.design_system.components.input.Input
import com.example.shared.extension.orEmpty

internal class InputValidator {
    private val validations = mutableListOf<InputValidation>()

    fun addValidation(validation: InputValidation) {
        validations.add(validation)
    }

    fun validate(input: Input): Boolean {
        val text = input.editText.text?.toString().orEmpty()

        validations.forEach { validation ->
            if (!validation.isValid(text)) {
                input.setInputDescription(validation.messageRes, Input.DescriptionType.ERROR, true)

                return false
            }
        }

        return true
    }
}

interface InputValidation {
    val messageRes: Int

    fun isValid(text: String): Boolean
}
