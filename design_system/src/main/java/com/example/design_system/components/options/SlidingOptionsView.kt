package com.example.design_system.components.options

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import com.example.design_system.R
import com.example.design_system.databinding.ItemSlidingOptionTextBinding
import com.example.shared.extension.onClick
import com.example.shared.extension.onGlobalLayout

class SlidingOptionsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    var indicatedItem: View? = null
        set(value) {
            field = value
            value?.let { item ->
                indicateView(item, indicatorView.width > ZERO_SIZE)
            }
        }

    var onIndicated: ((Int) -> Unit)? = null

    private var hasCalculatedSpacing = false
    private val optionsLinearLayout = LinearLayoutCompat(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    private val indicatorView = View(context).apply {
        layoutParams = LayoutParams(ZERO_SIZE, ZERO_SIZE)
        background = ContextCompat.getDrawable(context, R.drawable.background_rounded_8dp)
        background.setTint(context.getColor(R.color.foroom_secondary_green))
    }

    init {
        background = ContextCompat.getDrawable(context, R.drawable.background_rounded_8dp)
        background.setTint(context.getColor(R.color.foroom_layer_background))
        addView(indicatorView)
        addView(optionsLinearLayout)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        children.forEach { child ->
            if (child != optionsLinearLayout && child != indicatorView) {
                child.onClick { indicatedItem = child }
                removeView(child)
                optionsLinearLayout.addView(child)
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (!hasCalculatedSpacing) setOptionsSpacing()
        if (indicatedItem == null) indicatedItem = optionsLinearLayout.children.first()
    }

    fun addOption(optionText: String) {
        ItemSlidingOptionTextBinding.inflate(
            LayoutInflater.from(context),
            optionsLinearLayout,
            true
        ).apply {
            root.text = optionText
            root.onClick {
                indicatedItem = root
            }
        }
    }

    fun indicateAt(index: Int) {
        onGlobalLayout {
            indicatedItem = optionsLinearLayout.getChildAt(index)
        }
    }

    private fun setOptionsSpacing() {
        val combinedWidth = optionsLinearLayout.children.sumOf { child ->
            child.width
        }

        val itemSpacing = (width - combinedWidth) / (optionsLinearLayout.childCount - 1)

        optionsLinearLayout.children.forEach { child ->
            if (child != optionsLinearLayout.children.last()) {
                child.updateLayoutParams<MarginLayoutParams> {
                    rightMargin = itemSpacing
                }
            }
        }

        hasCalculatedSpacing = true
    }

    private fun indicateView(target: View, animate: Boolean = true) {
        indicatorView.layoutParams.height = target.height

        if (animate) {
            onIndicated?.invoke(optionsLinearLayout.indexOfChild(target))

            val positionAnimator = ValueAnimator.ofFloat(indicatorView.x, target.x)
            positionAnimator.interpolator = DecelerateInterpolator()
            positionAnimator.addUpdateListener { animator ->
                indicatorView.post {
                    indicatorView.translationX = animator.animatedValue as Float
                }
            }
            positionAnimator.start()

            val widthAnimator = ValueAnimator.ofInt(indicatorView.width, target.width)
            widthAnimator.interpolator = DecelerateInterpolator()
            widthAnimator.addUpdateListener { animator ->
                indicatorView.post {
                    indicatorView.layoutParams.width = animator.animatedValue as Int
                    indicatorView.requestLayout()
                }
            }

            widthAnimator.start()
        } else {
            indicatorView.layoutParams.width = target.width
            indicatorView.translationX = target.x
        }
    }

    companion object {
        private const val ZERO_SIZE = 0
    }
}