package com.example.design_system.components.image_chooser

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.children
import com.example.shared.model.Image
import com.example.shared.extension.dpToPx
import kotlin.math.ceil

class ImageChooserListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayoutCompat(context, attrs, defStyle) {
    private val horizontalArrangement = HorizontalArrangement.SPACE_BETWEEN
    private val chooserItemViews = mutableListOf<ImageChooserItemView>()

    var isChoosingEnabled = true
    var onImageChooseCallback: (Image) -> Unit = {}
    var columns = 3
    var selectedIndex = 0
        private set
    var images: List<Image> = emptyList()
        set(value) {
            field = value
            onDataChanged()
        }
    var rowSpacing: Int = DEFAULT_ROW_SPACING.dpToPx(context).toInt()
        set(value) {
            field = value
            adjustRowSpacing()
        }

    init {
        orientation = VERTICAL
    }

    private fun onDataChanged() {
        removeAllViews()
        chooserItemViews.clear()

        if (images.isEmpty()) {
            return
        }

        val rows = ceil(images.size / columns.toDouble()).toInt()

        for (row in 0 until rows) {
            val container = LinearLayoutCompat(context)
            container.orientation = HORIZONTAL

            for (col in 0 until columns) {
                container.addView(createImage(col, row))
                container.addView(createFillerView())
            }

            adjustFillerViews(container)
            addView(container)
        }

        adjustRowSpacing()
        onImageChooseCallback(images.first())
    }

    private fun createImage(column: Int, row: Int): View {
        val index = getImageIndex(row, column)

        return ImageChooserItemView(context).apply {
            setOnClickListener {
                onImageClick(column, row)
            }
            setImage(images[index])
            isImageSelected = index == selectedIndex
            chooserItemViews.add(this)
        }
    }

    private fun onImageClick(column: Int, row: Int) {
        if (!isChoosingEnabled) return

        val clickIndex = getImageIndex(row, column)

        if (clickIndex == selectedIndex) return

        chooserItemViews[selectedIndex].isImageSelected = false
        chooserItemViews[clickIndex].isImageSelected = true

        onImageChooseCallback(images[clickIndex])
        selectedIndex = clickIndex
    }

    private fun adjustFillerViews(container: LinearLayoutCompat) {
        if (horizontalArrangement == HorizontalArrangement.SPACE_BETWEEN) {
            container.removeView(container.children.last())
        } else {
            container.addView(createFillerView(), 0)
        }
    }

    private fun adjustRowSpacing() {
        children.forEachIndexed { index, row ->
            if (index != childCount - 1) {
                (row.layoutParams as MarginLayoutParams).bottomMargin = rowSpacing
            }
        }
    }

    private fun createFillerView() = View(context).apply {
        layoutParams = LayoutParams(
            0,
            0,
            1f
        )
    }

    private fun getImageIndex(row: Int, column: Int): Int {
        return row * columns + column
    }

    enum class HorizontalArrangement {
        SPACE_BETWEEN,
        SPACE_AROUND
    }

    companion object {
        private const val DEFAULT_ROW_SPACING = 16
    }
}