package com.example.design_system.components.nav_bar

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.example.design_system.R
import com.example.shared.extension.dpToPx
import com.example.shared.extension.onClick

class ForoomNavigationBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayoutCompat(context, attrs, defStyle) {
    private val navigationButtons = mutableListOf<View>()
    var onNavigationButtonClick: (View)-> Unit = {}

    init {
        setUp()
    }

    fun selectAt(index: Int) {
        onNavigationButtonClick(navigationButtons[index])
    }

    private fun setUp() {
        gravity = Gravity.CENTER_VERTICAL
        background = ContextCompat.getDrawable(context, R.drawable.background_nav_bar)

        val padding = VERTICAL_PADDING.dpToPx(context).toInt()
        setPadding(paddingLeft, padding, paddingRight, padding)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        children.toList().forEach { child ->
            addView(createFillerView(), children.indexOf(child))
            navigationButtons.add(child)

            child.onClick {
                onNavigationButtonClick(child)
            }
        }

        addView(createFillerView())
    }

    private fun onNavigationButtonClick(button: View) {
        navigationButtons.forEach { b ->
            b.isSelected = false
        }
        button.isSelected = true
        onNavigationButtonClick.invoke(button)
    }

    private fun createFillerView() = View(context).apply {
        layoutParams = LayoutParams(
            0, 0, 1f
        )
    }

    companion object {
        private const val VERTICAL_PADDING = 12
    }
}