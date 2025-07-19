package com.example.foroom.pagematchers


import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.data.Constants
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object HomePageMatchers {
    val profileNavButton: Matcher<View> by lazy {
        withId(R.id.homeNavigationProfile)
    }

    val createChatBttn: Matcher<View> by lazy { withId(R.id.homeNavigationCreateChat)}

    val userToFind: Matcher<View> by lazy {
        allOf(withId(com.example.design_system.R.id.chatTitleTextView),
            withText(Constants.CHAT_NAME1)
        )
    }

    val otherUserToFind: Matcher<View> by lazy {
        allOf(withId(com.example.design_system.R.id.chatTitleTextView),
            withText(Constants.CHAT_NAME2)
        )
    }

    val openChatButton: Matcher<View> by lazy {
        allOf(withId(R.id.sendMessageButton),
            hasSibling(userToFind)
        )
    }

    val openChatButton2: Matcher<View> by lazy {
        allOf(withId(R.id.sendMessageButton),
            hasSibling(otherUserToFind)
        )
    }

    val chatsNavigation: Matcher<View> by lazy { withId(R.id.homeNavigationChats)}

}