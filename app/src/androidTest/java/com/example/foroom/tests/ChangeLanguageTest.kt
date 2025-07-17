package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.Helper.Utils
import com.example.foroom.presentation.ui.activity.ForoomActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChangeLanguageTest:BaseTest() {
    @get:Rule
    val activity = ActivityScenarioRule(ForoomActivity::class.java)


    @Test
    fun changeLanguagesTest(){
        Utils.registerNewUserAndStay(userName, pass)

        homePageSteps.goToUserProfile()
        languageSteps.tapChangeLanguage()
            .chooseEnglishLangauge()
            .validateLanguageChangedToEnglish()
            .tapChangeLanguage()
            .chooseGeorgianLangauge()
            .validateLanguageChangedToGeorgian()
        profileSteps.logOut()

    }
}