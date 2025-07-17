package com.example.foroom.steps

import LoginPageMatchers
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import com.example.foroom.Helper.tap

class LoginSteps {
    fun enterUserAndPass(name: String, pass: String): LoginSteps {
        with(LoginPageMatchers) {
            onView(usernameField).perform(typeText(name), closeSoftKeyboard())
            onView(passwordField).perform(typeText(pass), closeSoftKeyboard())
        }
        return this;
    }

    fun clickLogin(): LoginSteps{
        with(LoginPageMatchers){
            loginButton.tap();
        }
        return this;
    }


    fun clickSignUp(): LoginSteps{
        with(LoginPageMatchers){
            registerButton.tap()
        }
        return this;
    }
}