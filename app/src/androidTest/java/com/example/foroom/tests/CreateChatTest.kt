package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.Helper.Utils
import com.example.foroom.data.Constants
import com.example.foroom.presentation.ui.activity.ForoomActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.OrderWith
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Alphanumeric

@RunWith(AndroidJUnit4::class)
@OrderWith(Alphanumeric::class)
class CreateChatTest: BaseTest(){
    @get:Rule
    val activity = ActivityScenarioRule(ForoomActivity::class.java)


    @Test
    fun createChatTest(){
        Utils.registerNewUserAndStay(userName, pass)

        homePageSteps.tapToCreateNewChat()
        createChatSteps.typeChatName(Constants.FULL_NAME)
            .chooseRandomImage()
            .tapCreateChatButton()
            .navigateBack()
            .goToCreatedChats()
            .validateChatCreated()
            .deleteChat()
        homePageSteps.goToUserProfile()
        profileSteps.logOut()
    }



}