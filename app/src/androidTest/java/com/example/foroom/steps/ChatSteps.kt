package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import com.example.foroom.Helper.isViewDisplayed
import com.example.foroom.Helper.swiper
import com.example.foroom.Helper.tap
import com.example.foroom.pagematchers.ChatPageMatchers
import com.example.foroom.pagematchers.CreateChatPageMatchers
import com.example.foroom.pagematchers.HomePageMatchers
import org.junit.Assert

class ChatSteps {

    fun typeInChat(text:String): ChatSteps{
        with(ChatPageMatchers) {
            onView(chatInput).perform(typeText(text), closeSoftKeyboard())
        }
        return this;
    }

    fun sendMessage(): ChatSteps{
        with(ChatPageMatchers) {
            sendMssgBttn.tap()
        }
        return this;
    }

    fun findConversationChat(): ChatSteps{
        with(ChatPageMatchers) {
            for(i in 1..3){
                if(!openChatButton3.isViewDisplayed()){
                    swipe()
                }
            }
        }
        return this;
    }

    private fun swipe(){
        swiper(400,200,20)
    }

    fun goToConversationChat(): ChatSteps{
        with(ChatPageMatchers) {
            Assert.assertTrue(conversationChat.isViewDisplayed())
            openChatButton3.tap()
        }
        return this;
    }




}