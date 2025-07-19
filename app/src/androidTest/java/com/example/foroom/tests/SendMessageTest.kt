package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.Helper.Utils
import com.example.foroom.data.Constants
import com.example.foroom.presentation.ui.activity.ForoomActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SendMessageTest: BaseTest(){
    @get:Rule
    val activity = ActivityScenarioRule(ForoomActivity::class.java)


    @Test
    fun sendMessageToMyself(){
        Utils.registerNewUserAndStay(userName, pass)

        homePageSteps.goToChats().findMyChat()

        chatSteps.typeInChat(Constants.DRINKK)
            .sendMessage()

        individualChatSteps.validateMessageWasSent(Constants.DRINKK)
            .goBack()

        homePageSteps.goToUserProfile()
        profileSteps.logOut()
    }

    @Test
    fun sendMessageToOther(){
        Utils.registerNewUserAndStay(userName, pass)

        homePageSteps.goToChats().findOtherChat()

        chatSteps.typeInChat(Constants.QUESTION)
            .sendMessage()

        individualChatSteps.validateMessageWasSent(Constants.QUESTION)
            .goBack()

        homePageSteps.goToUserProfile()
        profileSteps.logOut()
    }
}