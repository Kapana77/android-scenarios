package com.example.shared.util.language_change

import com.example.shared.model.ForoomLanguage

interface AppLanguageDelegate {
    fun changeAppLanguage(language: ForoomLanguage, changePoint: LanguageChangePoint)

    suspend fun getAppLanguage(): ForoomLanguage?
}

enum class LanguageChangePoint(val value: Int) {
    LOG_IN(1),
    REGISTRATION(2),
    POFILE(3);

    companion object {
        fun fromValue(value: Int) = entries.find { point -> point.value == value }
    }
}
