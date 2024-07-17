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

fun ForoomNavigationHost.popBackStack() {
    getHostFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

fun ForoomNavigationHost.openNextPage(
    fragment: BaseFragment<*, *>,
    addToBackStack: Boolean = true,
    popBackStack: Boolean = false
) {
    val transaction = getHostFragmentManager().beginTransaction()

    transaction.setCustomAnimations(
        R.anim.animation_slide_in_right,
        R.anim.dummy_animation,
        R.anim.dummy_animation,
        R.anim.animation_slide_out_right
    )

    transaction.add(
        fragmentContainerId,
        fragment
    )

    if (popBackStack) popBackStack()
    else if (addToBackStack) transaction.addToBackStack(null)

    transaction.commit()

}

fun ForoomNavigationHost.addOrShowPage(fragment: BaseFragment<*, *>) {
    val transaction = getHostFragmentManager().beginTransaction()

    if (fragment.isAdded) {
        getHostFragmentManager().fragments.forEach(transaction::hide)
        transaction.show(fragment)
    } else {
        transaction.add(
            fragmentContainerId,
            fragment
        )
    }

    transaction.commit()
}

fun <T : Parcelable> ForoomNavigationHost.openNextPage(
    fragment: BaseFragment<*, *>,
    args: T,
    addToBackStack: Boolean
) {
    openNextPage(fragment.apply {
        arguments = bundleOf(EXTRA_ARGUMENTS to args)
    }, addToBackStack)
}

internal const val EXTRA_ARGUMENTS = "arguments"
