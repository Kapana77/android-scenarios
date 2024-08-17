package com.example.design_system.components.image_chooser

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.design_system.components.image_view.CircleImageView
import com.example.design_system.components.selection_indicator.SelectionIndicatorView
import com.example.design_system.components.shimmer.ForoomShimmerDrawableBuilder
import com.example.shared.extension.dpToPx
import com.example.shared.extension.loadImageUrl
import com.example.shared.model.Image

class ImageChooserItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private var imageView: CircleImageView? = null
    private var selectionIndicatorView: SelectionIndicatorView? = null

    var isImageSelected: Boolean
        get() = selectionIndicatorView?.isExpanded == true
        set(value) {
            selectionIndicatorView?.setExpanded(value)
        }

    init {
        orientation = VERTICAL
        bindViews()
    }

    private fun bindViews() {
        val itemSizePx = ITEM_SIZE.dpToPx(context).toInt()

        imageView = CircleImageView(context).apply {
            layoutParams = LayoutParams(
                itemSizePx,
                itemSizePx
            ).apply {
                bottomMargin = MARGIN_SIZE.dpToPx(context).toInt()
            }
        }

        addView(imageView)

        selectionIndicatorView = SelectionIndicatorView(context).apply {
            layoutParams = LayoutParams(
                itemSizePx,
                INDICATOR_HEIGHT.dpToPx(context).toInt()
            )
        }

        addView(selectionIndicatorView)
    }

    fun setImage(image: Image) {
        imageView?.image?.loadImageUrl(
            image.url,
            ForoomShimmerDrawableBuilder.getDefaultDrawable(context)
        )
    }

    companion object {
        private const val ITEM_SIZE = 80
        private const val INDICATOR_HEIGHT = 2
        private const val MARGIN_SIZE = 8
    }
}