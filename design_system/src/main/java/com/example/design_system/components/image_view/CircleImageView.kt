package com.example.design_system.components.image_view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView

class CircleImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : MaterialCardView(context, attrs, defStyle) {
    private var _image: AppCompatImageView? = null
    val image get() = _image!!

    init {
        strokeWidth = 0
        _image = AppCompatImageView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        }

        addView(_image)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        radius = width / 2f
    }
}