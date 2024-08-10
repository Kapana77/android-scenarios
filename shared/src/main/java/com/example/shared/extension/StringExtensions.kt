package com.example.shared.extension

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.example.shared.util.span.ForoomClickableSpan

fun String.parseTextWithSingleSpan(
    spanColor: Int,
    onSpanClick: (() -> Unit)? = null
): SpannableString? {
    val startIndex = indexOf(SPAN_START_SYMBOL) - 1
    val endIndex = indexOf(SPAN_END_SYMBOL) - 1

    if (endIndex <= startIndex) return null

    val clearedText =
        replace(SPAN_START_SYMBOL, EMPTY_STRING).replace(SPAN_END_SYMBOL, EMPTY_STRING)

    val spannableString = SpannableString(clearedText)

    onSpanClick?.let { event ->
        spannableString.setSpan(
            ForoomClickableSpan(event), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    spannableString.setSpan(
        ForegroundColorSpan(spanColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return spannableString
}

fun String?.orEmpty() = this ?: EMPTY_STRING
fun String?.orNA() = this ?: NA_STRING

const val SPAN_START_SYMBOL = "["
const val SPAN_END_SYMBOL = "]"
const val EMPTY_STRING = ""
const val NA_STRING = "N/A"