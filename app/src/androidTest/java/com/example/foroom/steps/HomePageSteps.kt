package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import com.example.foroom.Helper.tap
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pagematchers.HomePageMatchers

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
}