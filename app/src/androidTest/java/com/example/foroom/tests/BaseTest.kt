package com.example.foroom.tests

import com.example.foroom.data.Constants
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.CreateChatSteps
import com.example.foroom.steps.HomePageSteps
import com.example.foroom.steps.IndividualChatSteps
import com.example.foroom.steps.LanguageSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import com.example.foroom.steps.RegisterSteps
import com.github.javafaker.Faker

open class BaseTest {
    protected val loginSteps = LoginSteps()
    protected val homePageSteps = HomePageSteps()
    protected val profileSteps = ProfileSteps()
    protected val registerSteps = RegisterSteps()
    protected val languageSteps = LanguageSteps()
    protected val createChatSteps = CreateChatSteps()
    protected val chatSteps = ChatSteps()
    protected val individualChatSteps = IndividualChatSteps()

    companion object {
        @JvmStatic
        protected val faker = Faker()
        @JvmStatic
        val userName: String = faker.name().username()

        @JvmStatic
        val pass: String = faker.internet()
            .password(9, 19, true, true) + Constants.STRONG_PASS_STRING

        @JvmStatic
        val newPass: String = faker.internet()
            .password(9, 19, true, true) + Constants.NEW_STRONG_PASS
    }
}