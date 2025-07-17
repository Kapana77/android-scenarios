package com.example.foroom.pagematchers


import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object RegisterPageMatchers {
    val registerContainer: Matcher<View> by lazy {
        allOf(
            withId(R.id.userNameInput),
            hasSibling(withId(R.id.repeatPasswordInput))
        )
    }

    val passContainer: Matcher<View> by lazy {
        allOf(
            withId(R.id.passwordInput),
            hasSibling(withId(R.id.repeatPasswordInput))
        )
    }

    val userNameField: Matcher<View> by lazy {
        allOf(
            withHint("მომხმარებლის სახელი"),
            isDisplayed(),
            isDescendantOfA(registerContainer)

        )
    }

    val passwordField: Matcher<View> by lazy {
        allOf(
            withHint("პაროლი"),
            isDisplayed(),
            isDescendantOfA(passContainer)

        )
    }
    val confirmPasswordField: Matcher<View> by lazy {
        allOf(
            withHint("გაიმეორეთ პაროლი"),
            isDescendantOfA(withId(R.id.repeatPasswordInput))
        )
    }

    val signUpBttn: Matcher<View> by lazy {
        allOf(
            withId(R.id.signUpButton),
            hasSibling(withId(R.id.repeatPasswordInput))
        )
    }

}