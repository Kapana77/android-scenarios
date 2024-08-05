package com.example.design_system.components.search_bar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.design_system.R
import com.example.design_system.databinding.LayoutForoomSearchBarBinding

class ForoomSearchBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding =
        LayoutForoomSearchBarBinding.inflate(LayoutInflater.from(context), this, true)

    val input get() = binding.searchInput

    init {
        resources.obtainAttributes(attrs, R.styleable.ForoomSearchBar).apply {
            binding.searchInput.editText.hint = getString(R.styleable.ForoomSearchBar_hint)
        }.recycle()
    }
}