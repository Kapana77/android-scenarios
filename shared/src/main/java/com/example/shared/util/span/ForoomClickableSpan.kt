package com.example.shared.util.span

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class ForoomClickableSpan(private val onClick: ()-> Unit): ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = false
    }

    override fun onClick(widget: View) {
        this.onClick()
    }
}