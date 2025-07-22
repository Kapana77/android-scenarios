package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.getText
import com.example.foroom.Helper.isViewDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pagematchers.IndividualChatPageMatchers
import org.junit.Assert

class IndividualChatSteps {
    fun validateMessageWasSent(mesg: String): IndividualChatSteps{
        with(IndividualChatPageMatchers) {
//            onView(messageSent).waitUntilVisible(5)
            Assert.assertEquals(messageSent.getText(), mesg)
        }
        return this;
    }


    fun goBack(): IndividualChatSteps{
        pressBack()
        return this;
    }


    fun findAndValidateGreetingMessage(username:String,expectedMessage:String): IndividualChatSteps{
        with(IndividualChatPageMatchers) {

//            Thread.sleep(3000)

//            onView(messageFromFirstUser(username))
//                .check(matches(withText(expectedMessage)))


            Assert.assertEquals(
                messageFromFirstUser(username).getText(),expectedMessage
            )
            Assert.assertEquals(
                userName(username).getText(),username
            )
        }
        return this;
    }



}