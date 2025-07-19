package com.example.foroom.pagematchers

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.data.Constants
import com.example.foroom.pagematchers.HomePageMatchers.otherUserToFind
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object ChatPageMatchers {

    val sendMssgBttn: Matcher<View> by lazy { withId(R.id.sendMessageButton) }

    val chatInput: Matcher<View> by lazy {
        allOf(
            withId(com.example.design_system.R.id.inputEditText),
            isDescendantOfA(withId(R.id.messageInput))
        )
    }


    val conversationChat: Matcher<View> by lazy {
        allOf(withId(com.example.design_system.R.id.chatTitleTextView),
            withText("ConversationWithFriends")
        )
    }

    val openChatButton3: Matcher<View> by lazy {
        allOf(withId(R.id.sendMessageButton),
            hasSibling(conversationChat)
        )
    }




}