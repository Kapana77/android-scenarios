package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.Helper.Utils
import com.example.foroom.presentation.ui.activity.ForoomActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.OrderWith
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Alphanumeric

@RunWith(AndroidJUnit4::class)
@OrderWith(Alphanumeric::class)

class ChangePasswordTest:BaseTest() {
    @get:Rule
    val activity = ActivityScenarioRule(ForoomActivity::class.java)


    @Test
    fun A_setUp(){
        Utils.registerNewUser(userName, pass)
    }


    @Test
    fun B_logIn(){
        loginSteps.enterUserAndPass(userName,pass)
            .clickLogin()
        homePageSteps.goToUserProfile()
    }

    @Test
    fun C_navigateToProfile(){
        homePageSteps.goToUserProfile()
        profileSteps.tapChangePassword()
        profileSteps.enterNewPass(newPass)
            .confirmNewPass(newPass)
        profileSteps.tapChangePass()
    }

    @Test
    fun D_typeNewPassLogin(){
        loginSteps.enterUserAndPass(userName,newPass)
        loginSteps.clickLogin()
        homePageSteps.goToUserProfile()
        profileSteps.validateUserRegistered(userName)
            .logOut()
    }

}