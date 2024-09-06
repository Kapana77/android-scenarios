package com.example.shared.model

enum class ForoomLanguage(val langName: String) {
    EN("en"), KA("ka");

    companion object {
        fun fromName(name: String) = entries.find { language -> language.langName == name }
    }
}