package com.example.foroom.Helper

import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.steps.HomePageSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import com.example.foroom.steps.RegisterSteps
import com.example.foroom.tests.BaseTest.Companion.pass
import com.example.foroom.tests.BaseTest.Companion.userName
import org.hamcrest.Matchers.allOf
import kotlin.random.Random

class Utils {

    companion object {
        private val loginSteps = LoginSteps();
        private val registerSteps = RegisterSteps();
        private val homePageSteps = HomePageSteps()
        private val profileSteps = ProfileSteps()
        fun registerNewUser(username:String,password:String){
            loginSteps.clickSignUp()

            registerSteps.enterUserAndPass(userName, pass)
                .chooseRandomIcon()
                .register()

            homePageSteps.goToUserProfile()

            profileSteps.validateUserRegistered(userName)
                .logOut()
        }

        fun registerNewUserAndStay(username:String,password:String){
            loginSteps.clickSignUp()

            registerSteps.enterUserAndPass(username, password)
                .chooseRandomIcon()
                .register()

            homePageSteps.goToUserProfile()

            profileSteps.validateUserRegistered(username)
        }

        fun clickRandomIcon() {
            val itemCount = 6
            val randomIndex = Random.nextInt(itemCount)

            onView(
                withIndex(
                    allOf(
                        isDescendantOfA(withId(R.id.listView)),
                        isAssignableFrom(ImageView::class.java)
                    ),
                    randomIndex
                )
            ).perform(click())
        }

        fun tapRandomItemInList() {
            val itemCount = 9
            val randomIndex = Random.nextInt(itemCount)

            onView(
                withIndex(
                    allOf(
                        isDescendantOfA(withId(R.id.chatImageChooser)),
                        isAssignableFrom(ImageView::class.java)
                    ),
                    randomIndex
                )
            ).perform(click())
        }
    }


}