package com.example.foroom.presentation.ui.util.locale_manager

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object ForoomLocaleManager {
    fun wrapContext(context: Context?, language: String): Context? {
        val savedLocale = Locale(language)

        // as part of creating a new context that contains the new locale we also need to override the default locale.
        Locale.setDefault(savedLocale)

        // create new configuration with the saved locale
        val newConfig = Configuration()
        newConfig.setLocale(savedLocale)

        return context?.createConfigurationContext(newConfig)
    }

    enum class ForoomLocale(val code: String) {
        EN("en"),
        GE("ka")
    }
}