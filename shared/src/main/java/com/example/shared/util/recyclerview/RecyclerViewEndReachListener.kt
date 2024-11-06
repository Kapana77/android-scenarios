package com.example.shared.util.recyclerview

import androidx.recyclerview.widget.RecyclerView

// todo research if we need this
class RecyclerViewEndReachListener(
    private val type: Type,
    private val onBottomReach: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val direction = if (type == Type.BOTTOM) SCROLL_DIRECTION_DOWN else SCROLL_DIRECTION_UP
        if (!recyclerView.canScrollVertically(direction)) onBottomReach()
    }

    enum class Type {
        BOTTOM,
        TOP
    }

    companion object {
        private const val SCROLL_DIRECTION_DOWN = 1
        private const val SCROLL_DIRECTION_UP = -1
    }
}