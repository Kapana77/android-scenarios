package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.Utils
import com.example.foroom.Helper.getText
import com.example.foroom.Helper.tap
import com.example.foroom.data.Constants
import com.example.foroom.pagematchers.CreateChatPageMatchers
import org.junit.Assert

class CreateChatSteps {
    fun typeChatName(chatName:String): CreateChatSteps{
        with(CreateChatPageMatchers) {
            onView(chatNameInput).perform(typeText(chatName), closeSoftKeyboard())
        }
        return this;
    }

    fun chooseRandomImage(): CreateChatSteps{
        Utils.tapRandomItemInList()

        return this;
    }

    fun tapCreateChatButton(): CreateChatSteps{
        with(CreateChatPageMatchers) {
            createChatBttn.tap()
        }
        return this;
    }


    fun navigateBack(): CreateChatSteps{
        pressBack()
        return this;
    }

    fun goToCreatedChats(): CreateChatSteps{
        with(CreateChatPageMatchers) {
            createdChatsBttn.tap()
        }
        return this;
    }

    fun validateChatCreated(): CreateChatSteps{
        with(CreateChatPageMatchers) {
            onView(createdChatsName).check(matches(isDisplayed()))
            Assert.assertEquals(createdChatsName.getText(), Constants.FULL_NAME)
        }
        return this;
    }

    fun deleteChat(): CreateChatSteps{
        with(CreateChatPageMatchers) {
            deleteChatBtn.tap()
        }
        return this;
    }


}