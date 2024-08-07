package com.example.design_system.components.search_bar

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import com.example.design_system.R
import com.example.design_system.databinding.LayoutForoomSearchBarBinding
import com.example.shared.extension.hide
import com.example.shared.extension.onClick
import com.example.shared.extension.show

class ForoomSearchBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding =
        LayoutForoomSearchBarBinding.inflate(LayoutInflater.from(context), this, true)

    val input get() = binding.searchInput

    var isCollapsed: Boolean = false
        set(value) {
            field = value
            if (isCollapsed) showFilterView() else hideFilterView()
        }

    init {
        resources.obtainAttributes(attrs, R.styleable.ForoomSearchBar).apply {
            binding.searchInput.editText.hint = getString(R.styleable.ForoomSearchBar_hint)
        }.recycle()

        binding.showFilterButton.onClick {
            isCollapsed = !isCollapsed
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (childCount > 1) {
            val collapseView = getChildAt(1)

            removeView(collapseView)
            binding.collapsableContainerView.addView(collapseView)
        }
    }

    private fun showFilterView() {
        binding.collapsableContainerView.show()

        val alphaAnimation = ValueAnimator.ofFloat(
            PARTIAL_ALPHA,
            FULL_ALPHA
        ).apply {
            duration = ANIMATION_DURATION
        }

        val positionAnimation = ValueAnimator.ofFloat(
            NEGATIVE_TRANSLATION, ZERO_TRANSLATION
        ).apply {
            duration = ANIMATION_DURATION
        }

        alphaAnimation.addUpdateListener { animator ->
            binding.collapsableContainerView.alpha = animator.animatedValue as Float
        }

        positionAnimation.addUpdateListener { animator ->
            binding.collapsableContainerView.translationY = animator.animatedValue as Float
        }

        alphaAnimation.start()
        positionAnimation.start()
    }

    private fun hideFilterView() {
        val alphaAnimation = ValueAnimator.ofFloat(
            FULL_ALPHA,
            PARTIAL_ALPHA
        ).apply {
            duration = ANIMATION_DURATION
        }

        val positionAnimation = ValueAnimator.ofFloat(
            ZERO_TRANSLATION, NEGATIVE_TRANSLATION
        ).apply {
            duration = ANIMATION_DURATION
        }

        alphaAnimation.addUpdateListener { animator ->
            binding.collapsableContainerView.alpha = animator.animatedValue as Float
        }

        positionAnimation.addUpdateListener { animator ->
            binding.collapsableContainerView.translationY = animator.animatedValue as Float
        }

        positionAnimation.doOnEnd {
            binding.collapsableContainerView.hide()
        }

        alphaAnimation.start()
        positionAnimation.start()
    }

    companion object {
        private const val PARTIAL_ALPHA = 0.2F
        private const val FULL_ALPHA = 1F

        private const val NEGATIVE_TRANSLATION = -50f
        private const val ZERO_TRANSLATION = 0f

        private const val ANIMATION_DURATION = 150L
    }
}