package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import com.example.foroom.Helper.isViewDisplayed
import com.example.foroom.Helper.tap
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pagematchers.HomePageMatchers
import org.junit.Assert

class HomePageSteps {

    fun goToUserProfile(): HomePageSteps {
        with(HomePageMatchers) {
            onView(profileNavButton).waitUntilVisible(5)
            onView(profileNavButton).tap()
        }
        return this;
    }

    fun tapToCreateNewChat(): HomePageSteps {
        with(HomePageMatchers) {
            createChatBttn.tap()
        }
        return this;
    }


    fun goToChats(): HomePageSteps {
        with(HomePageMatchers) {
            chatsNavigation.tap()
        }
        return this;
    }


    fun findMyChat(): HomePageSteps {
        with(HomePageMatchers) {
            Assert.assertTrue(userToFind.isViewDisplayed())
            openChatButton.tap()
        }
        return this;
    }

    fun findOtherChat(): HomePageSteps {
        with(HomePageMatchers) {
            Assert.assertTrue(otherUserToFind.isViewDisplayed())
            openChatButton2.tap()
        }
        return this;
    }
}