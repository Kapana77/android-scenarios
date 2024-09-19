package com.example.shared.util.runtime.user_language

import com.example.shared.model.ForoomLanguage

interface UserLanguageRuntimeHolder {

    fun setUserLanguage(language: ForoomLanguage)

    fun getUserLanguage(): ForoomLanguage
}