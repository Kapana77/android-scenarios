package com.example.shared.extension

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.method.LinkMovementMethodCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.shared.util.lock.SafeInteractionLock
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

private const val SAFE_CLICK_DELAY = 500L
fun View.onClick(block: () -> Unit) {
    setOnClickListener(SafeClickListener(SAFE_CLICK_DELAY, block))
}

private class SafeClickListener(
    private val delay: Long,
    private val block: () -> Unit
) : View.OnClickListener, KoinComponent {
    private val safeInteractionLock: SafeInteractionLock = get<SafeInteractionLock> {
        parametersOf(delay)
    }.apply {
        interactionCallBack = block
    }

    override fun onClick(v: View?) {
        safeInteractionLock.interact()
    }
}

fun Context.getDrawableFromAttribute(attributeId: Int): Drawable? {
    val typedValue = TypedValue().also { theme.resolveAttribute(attributeId, it, true) }
    return ContextCompat.getDrawable(this, typedValue.resourceId)
}

fun Int.dpToPx(context: Context): Float {
    val displayMetrics = context.resources.displayMetrics;
    return this * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
}

fun Float.dpToPx(context: Context): Float {
    val displayMetrics = context.resources.displayMetrics;
    return this * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
}

fun TextView.makeTextClickable(spanColor: Int, onClickEvent: (() -> Unit)? = null) {
    highlightColor = Color.TRANSPARENT
    movementMethod = LinkMovementMethodCompat.getInstance()
    val spannedText = text.toString().parseTextWithSingleSpan(spanColor, onClickEvent)
    text = spannedText
}

fun ImageView.loadImageUrl(url: String, placeHolder: Drawable? = null) {
    Glide.with(context).load(url)
        .placeholder(placeHolder)
        .centerCrop()
        .into(this)
}

fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}
