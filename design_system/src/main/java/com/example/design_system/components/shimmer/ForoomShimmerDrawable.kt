package com.example.design_system.components.shimmer

import android.content.Context
import com.example.design_system.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class ForoomShimmerDrawableBuilder {
    private var duration = 1000L
    private var baseAlpha = 0.7f
    private var highlightAlpha = 0.2f
    private var direction = Shimmer.Direction.LEFT_TO_RIGHT
    private var autoStart = true
    private var baseColorRes = R.color.foroom_layer_background
    private var highlightColorRes = R.color.foroom_gray_light

    fun build(context: Context): ShimmerDrawable {
        val shimmer = Shimmer.ColorHighlightBuilder()
            .setDuration(duration)
            .setBaseAlpha(baseAlpha)
            .setBaseColor(context.getColor(baseColorRes))
            .setHighlightColor(context.getColor(highlightColorRes))
            .setHighlightAlpha(highlightAlpha)
            .setDirection(direction)
            .setAutoStart(autoStart)
            .build()

        return ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
    }

    fun setDuration(duration: Long): ForoomShimmerDrawableBuilder {
        this.duration = duration
        return this
    }

    fun setBaseAlpha(baseAlpha: Float): ForoomShimmerDrawableBuilder {
        this.baseAlpha = baseAlpha
        return this
    }

    fun setHighlightAlpha(highlightAlpha: Float): ForoomShimmerDrawableBuilder {
        this.highlightAlpha = highlightAlpha
        return this
    }

    fun setDirection(direction: Int): ForoomShimmerDrawableBuilder {
        this.direction = direction
        return this
    }

    fun setAutoStart(autoStart: Boolean): ForoomShimmerDrawableBuilder {
        this.autoStart = autoStart
        return this
    }

    fun setBaseColorRes(baseColorRes: Int): ForoomShimmerDrawableBuilder {
        this.baseColorRes = baseColorRes
        return this
    }

    fun setHighlightColorRes(highlightColorRes: Int): ForoomShimmerDrawableBuilder {
        this.highlightColorRes = highlightColorRes
        return this
    }

    companion object {
        fun getDefaultDrawable(context: Context): ShimmerDrawable {
            return ForoomShimmerDrawableBuilder().build(context)
        }
    }
}