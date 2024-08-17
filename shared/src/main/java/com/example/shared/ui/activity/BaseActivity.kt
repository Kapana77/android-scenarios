package com.example.shared.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding

open class BaseActivity<T: ViewBinding>(private val inflate: (LayoutInflater)-> T): FragmentActivity() {
    protected lateinit var binding: T
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }
}