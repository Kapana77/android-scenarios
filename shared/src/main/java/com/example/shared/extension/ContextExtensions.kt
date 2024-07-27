package com.example.shared.extension

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.util.Size
import android.view.WindowManager
import android.widget.Toast

fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.getScreenSize(): Size {
    val wm = checkNotNull(getSystemService(WindowManager::class.java)) { "wm == null" }

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val metrics = wm.currentWindowMetrics
        Size(metrics.bounds.width(), metrics.bounds.height())
    } else {
        val display = wm.defaultDisplay
        val metrics = if (display != null) {
            DisplayMetrics()
        } else {
            Resources.getSystem().displayMetrics
        }
        display.getRealMetrics(metrics)
        Size(metrics.widthPixels, metrics.heightPixels)
    }
}

fun Context.getScreenWidth() = getScreenSize().width

fun Context.getScreenHeight() = getScreenSize().height

fun Context.screenWidthOf(percent: Float) = getScreenWidth() * percent

fun Context.screenHeightOf(percent: Float) = getScreenHeight() * percent
