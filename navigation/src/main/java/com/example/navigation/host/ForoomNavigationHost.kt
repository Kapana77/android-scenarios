package com.example.navigation.host

import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.example.navigation.R
import com.example.shared.ui.fragment.BaseFragment

interface ForoomNavigationHost {
    fun getHostFragmentManager(): FragmentManager
    val fragmentContainerId: Int
    fun goBack()
}

fun ForoomNavigationHost.openNextPage(
    fragment: BaseFragment<*, *>,
    addToBackStack: Boolean = true,
    popBackStack: Boolean = false,
    animate: Boolean = true
) {
    val transaction = getHostFragmentManager().beginTransaction()

    if (animate)  {
        transaction.setCustomAnimations(
            R.anim.animation_slide_in_right,
            R.anim.dummy_animation,
            R.anim.dummy_animation,
            R.anim.animation_slide_out_right
        )
    }

    if (popBackStack) {
        getHostFragmentManager().fragments.forEach(transaction::remove)
    } else if (addToBackStack) transaction.addToBackStack(null)

    transaction.add(
        fragmentContainerId,
        fragment
    )

    transaction.commit()
}

fun <T : Parcelable> ForoomNavigationHost.openNextPage(
    fragment: BaseFragment<*, *>,
    args: T,
    addToBackStack: Boolean = true,
    animate: Boolean = true
) {
    openNextPage(fragment.apply {
        arguments = bundleOf(EXTRA_ARGUMENTS to args)
    }, addToBackStack = addToBackStack, animate = animate)
}

fun ForoomNavigationHost.removeFragment(fragment: BaseFragment<*, *>) {
    getHostFragmentManager().beginTransaction().remove(fragment).commit()
}

internal const val EXTRA_ARGUMENTS = "arguments"
