package com.example.navigation.guest

import android.os.Bundle
import android.os.Parcelable
import com.example.navigation.host.EXTRA_ARGUMENTS

interface ForoomNavigationArgumentsHolder<T> {
    val bundle: Bundle?
    val argClass: Class<T>
}

fun <T: Parcelable> ForoomNavigationArgumentsHolder<T>.setNavArgs(args: T) {
    bundle?.putParcelable(EXTRA_ARGUMENTS, args)
}

val <T : Parcelable> ForoomNavigationArgumentsHolder<T>.navArgs
    get() = if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.TIRAMISU) {
        bundle?.getParcelable(EXTRA_ARGUMENTS, argClass)
    } else {
        bundle?.getParcelable(EXTRA_ARGUMENTS)
    }

val <T : Parcelable> ForoomNavigationArgumentsHolder<T>.requireNavArgs get() = navArgs!!
