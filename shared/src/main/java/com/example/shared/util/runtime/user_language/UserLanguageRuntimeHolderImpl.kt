package com.example.shared.util.runtime.user_language

import com.example.shared.model.ForoomLanguage

class UserLanguageRuntimeHolderImpl: UserLanguageRuntimeHolder {
    private var userLanguage: ForoomLanguage = ForoomLanguage.KA

    override fun setUserLanguage(language: ForoomLanguage) {
        userLanguage = language
    }

    override fun getUserLanguage(): ForoomLanguage {
        return userLanguage
    }
}