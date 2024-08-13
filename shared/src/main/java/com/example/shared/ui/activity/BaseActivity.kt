package com.example.shared.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.example.shared.model.Result
import com.example.shared.ui.ResultHandler

open class BaseActivity<T: ViewBinding>(private val inflate: (LayoutInflater)-> T): FragmentActivity() {
    protected lateinit var binding: T
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    protected fun <T> LiveData<Result<T>>.handleResult(block: ResultHandler<T>.() -> Unit) =
        ResultHandler<T>().apply(block).handle(this, this@BaseActivity)
}