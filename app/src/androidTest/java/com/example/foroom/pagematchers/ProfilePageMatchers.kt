package com.example.foroom.pagematchers

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object ProfilePageMatchers {
    val userName: Matcher<View> by lazy { withId(R.id.userNameTextView) }
    val logOutBtn: Matcher<View> by lazy { withId(R.id.signOutItem) }
    val changePassword: Matcher<View> by lazy { withId(R.id.changePasswordItem) }
    val newPassContainer: Matcher<View> by lazy {
        allOf(
            withId(R.id.passwordInput),
            hasSibling(withId(R.id.repeatPasswordInput))
        )
    }
    val newPassInput: Matcher<View> by lazy {
        allOf(
            withId(com.example.design_system.R.id.inputEditText),
            isDescendantOfA(newPassContainer)
        )
    }

    val repeatPassInput: Matcher<View> by lazy {
        allOf(
            withId(com.example.design_system.R.id.inputEditText),
            isDescendantOfA(withId(R.id.repeatPasswordInput))
        )
    }

    val confirmChangePass: Matcher<View> by lazy {
        allOf(
            withId(com.example.design_system.R.id.actionButton),
            withText("დადასტურება")
        )
    }







}