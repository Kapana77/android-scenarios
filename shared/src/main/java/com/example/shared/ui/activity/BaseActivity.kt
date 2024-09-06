package com.example.shared.ui.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import java.util.Locale

open class BaseActivity<T: ViewBinding>(private val inflate: (LayoutInflater)-> T): FragmentActivity() {
    protected lateinit var binding: T
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    protected fun wrapContext(context: Context?, language: String): Context? {
        val savedLocale = Locale(language)

        // as part of creating a new context that contains the new locale we also need to override the default locale.
        Locale.setDefault(savedLocale)

        // create new configuration with the saved locale
        val newConfig = Configuration()
        newConfig.setLocale(savedLocale)

        return context?.createConfigurationContext(newConfig)
    }
}