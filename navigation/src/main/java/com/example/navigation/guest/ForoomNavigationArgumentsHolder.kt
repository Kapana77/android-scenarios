package com.example.navigation.guest

import android.os.Bundle
import android.os.Parcelable
import com.example.navigation.host.EXTRA_ARGUMENTS

interface ForoomNavigationArgumentsHolder {
    val guestArguments: Bundle?
}

fun <T : Parcelable> ForoomNavigationArgumentsHolder.navigationArguments(argClass: Class<T>): T {
    return if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.TIRAMISU) {
        guestArguments?.getParcelable(EXTRA_ARGUMENTS, argClass)
    } else {
        guestArguments?.getParcelable(EXTRA_ARGUMENTS)
    }!!
}
