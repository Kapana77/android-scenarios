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
class ConversationTest:BaseTest() {
    @get:Rule
    val activity = ActivityScenarioRule(ForoomActivity::class.java)


    val userName2: String = faker.name().username()
    val pass2: String = faker.internet()
        .password(9, 19, true, true) + Constants.STRONG_PASS_STRING


    @Test
    fun A_conversationWithFriends2Users(){
        Utils.registerNewUserAndStay(userName, pass)

        homePageSteps.goToChats()

        chatSteps.findConversationChat()
            .goToConversationChat()
            .typeInChat(Constants.GREET_MESSAGE)
            .sendMessage()

        individualChatSteps.validateMessageWasSent(Constants.GREET_MESSAGE)
            .goBack()

        homePageSteps.goToUserProfile()
        profileSteps.logOut()


    }

    @Test
    fun B_logWithSecondUserAndVerifyMessage(){
        Utils.registerNewUserAndStay(userName2, pass2)

        homePageSteps.goToChats()

        chatSteps.findConversationChat()
            .goToConversationChat()

        individualChatSteps.findAndValidateGreetingMessage(userName,Constants.GREET_MESSAGE)

        chatSteps.typeInChat(Constants.RESPONSE)
            .sendMessage()

        individualChatSteps.validateMessageWasSent(Constants.RESPONSE)
            .goBack()

        homePageSteps.goToUserProfile()
        profileSteps.logOut()

    }




}