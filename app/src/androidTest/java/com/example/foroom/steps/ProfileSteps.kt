package com.example.foroom.steps


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import com.example.foroom.Helper.getText
import com.example.foroom.Helper.tap
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pagematchers.ProfilePageMatchers
import org.junit.Assert

class ProfileSteps {

    fun validateUserRegistered(actualUserName: String): ProfileSteps{
        with(ProfilePageMatchers) {
            onView(userName).waitUntilVisible(3)
            Assert.assertEquals(userName.getText(),actualUserName)
        }
        return this;
    }

    fun logOut(): ProfileSteps{
        with(ProfilePageMatchers) {
            logOutBtn.tap()
        }
        return this;
    }

    fun tapChangePassword(): ProfileSteps{
        with(ProfilePageMatchers) {
            changePassword.tap()
        }
        return this;
    }

    fun enterNewPass(newPass:String): ProfileSteps{
        with(ProfilePageMatchers) {
            onView(newPassInput).perform(typeText(newPass), closeSoftKeyboard())
        }
        return this;
    }

    fun confirmNewPass(newPass:String): ProfileSteps{
        with(ProfilePageMatchers) {
            onView(repeatPassInput).perform(typeText(newPass), closeSoftKeyboard())
        }
        return this;
    }

    fun tapChangePass(): ProfileSteps{
        with(ProfilePageMatchers) {
            confirmChangePass.tap()
        }
        return this;
    }
}