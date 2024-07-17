package com.example.design_system.components.loader

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ViewAnimator
import com.example.design_system.R
import java.util.EnumMap

class ContentLoaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ViewAnimator(context, attrs) {
    private val inflatedViews = EnumMap<ViewType, Int>(ViewType::class.java)
    private var loaderLayoutId: Int
    private var startLoading: Boolean

    init {
        resources.obtainAttributes(attrs, R.styleable.ContentLoaderView).apply {
            loaderLayoutId =
                getResourceId(R.styleable.ContentLoaderView_loading_layout, BLANK_LOADER_ID)
            startLoading = getBoolean(R.styleable.ContentLoaderView_start_loading, false)
        }.recycle()

        inflatedViews[ViewType.CONTENT] = CONTENT_INDEX
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (childCount > 2) {
            throw Exception("Only content and empty page children are permitted")
        }

        if (childCount > 1) {
            inflatedViews[ViewType.EMPTY] = EMPTY_PAGE_INDEX
        }

        if (startLoading) {
            showLoader()
        }
    }

    fun showLoader() {
        if (loaderLayoutId == BLANK_LOADER_ID) throw Exception("No loader layout was provided")

        if (!inflatedViews.containsKey(ViewType.LOADER)) {
            LayoutInflater.from(context).inflate(loaderLayoutId, this, true)
            inflatedViews[ViewType.LOADER] = getNewIndex()
        }

        displayedChild = inflatedViews.getValue(ViewType.LOADER)
    }

    fun showEmptyPage() {
        if (!inflatedViews.containsKey(ViewType.EMPTY)) {
            throw Exception("No empty page was provided")
        }

        displayedChild = inflatedViews.getValue(ViewType.EMPTY)
    }

    fun showContent() {
        displayedChild = inflatedViews.getValue(ViewType.CONTENT)
    }

    private fun getNewIndex() = inflatedViews.values.max().inc()

    companion object {
        private const val BLANK_LOADER_ID = 0
        private const val CONTENT_INDEX = 0
        private const val EMPTY_PAGE_INDEX = 1
    }

    private enum class ViewType {
        CONTENT,
        LOADER,
        EMPTY
    }
}