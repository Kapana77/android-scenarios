package com.example.design_system.components.selection_indicator

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.design_system.R
import com.example.shared.util.lock.SafeInteractionLock
import com.example.shared.util.lock.SafeInteractionLockImpl
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SelectionIndicatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : View(context, attrs, defStyle), KoinComponent {
    var indicatorColor: Int = context.getColor(R.color.foroom_main_green)
        private set(value) {
            field = value
            paint.color = value
        }
    var isExpanded = true
        private set
    var animationDuration: Long = ANIMATION_DURATION

    private val safeInteractionLock: SafeInteractionLock = get {
        parametersOf(animationDuration)
    }

    private val accelerateDecelerateInterpolator = AccelerateDecelerateInterpolator()
    private val middlePoint get() = width / HALF_DIVIDER
    private var animationProgress = POINT_ZERO
    private val animator
        get() = if (isExpanded) ValueAnimator.ofFloat(POINT_ZERO, width / HALF_DIVIDER)
        else ValueAnimator.ofFloat(width / HALF_DIVIDER, POINT_ZERO)
    private val paint = Paint().apply {
        color = indicatorColor
        style = Paint.Style.FILL
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.SelectionIndicatorView).apply {
            indicatorColor = getColor(
                R.styleable.SelectionIndicatorView_colorRes,
                context.getColor(R.color.foroom_main_green)
            )
            animationDuration = getInteger(
                R.styleable.SelectionIndicatorView_animationDuration, ANIMATION_DURATION.toInt()
            ).toLong()
            isExpanded = getBoolean(R.styleable.SelectionIndicatorView_isExpanded, true)
        }.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        animationProgress = if (isExpanded) {
            widthMeasureSpec / HALF_DIVIDER
        } else 0f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(createRect(animationProgress), paint)
    }

    fun setExpanded(expanded: Boolean) {
        if (safeInteractionLock.isSafeToInteract && isExpanded != expanded) {
            safeInteractionLock.interact()
            isExpanded = expanded
            animateIndication()
        }
    }

    fun setColor(color: Int) {
        indicatorColor = color
        postInvalidate()
    }

    private fun animateIndication() {
        post {
            animator.apply {
                duration = animationDuration
                interpolator = accelerateDecelerateInterpolator

                addUpdateListener { animator ->
                    animationProgress = animator.animatedValue as Float
                    postInvalidate()
                }
            }.start()
        }
    }

    private fun createRect(animatedValue: Float): RectF {
        return RectF(
            middlePoint - animatedValue, 0f, middlePoint + animatedValue, height.toFloat()
        )
    }

    companion object {
        private const val ANIMATION_DURATION = 150L
        private const val HALF_DIVIDER = 2F
        private const val POINT_ZERO = 0F
    }
}