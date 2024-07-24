package com.example.shared.util.recyclerview

import androidx.recyclerview.widget.RecyclerView

class RecyclerViewBottomReachListener(
    private val onBottomReach: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollVertically(1)) onBottomReach()
    }

}