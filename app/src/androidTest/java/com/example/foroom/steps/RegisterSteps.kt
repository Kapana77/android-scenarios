package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import com.example.foroom.Helper.Utils
import com.example.foroom.Helper.tap
import com.example.foroom.pagematchers.RegisterPageMatchers

class RegisterSteps {
    fun enterUserAndPass(name:String,pass:String): RegisterSteps {
        with(RegisterPageMatchers) {
            onView(userNameField).perform(typeText(name), closeSoftKeyboard())
            onView(passwordField).perform(typeText(pass), closeSoftKeyboard())
            onView(confirmPasswordField).perform(typeText(pass), closeSoftKeyboard())
        }
        return this;
    }

    fun chooseRandomIcon(): RegisterSteps {
        Utils.clickRandomIcon()
        return this;
    }

    fun register(): RegisterSteps {
        with(RegisterPageMatchers) {
            signUpBttn.tap()
        }
        return this;
    }
}