package com.example.foroom.pagematchers

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.data.Constants
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object CreateChatPageMatchers {

    val chatNameInput: Matcher<View> by lazy {
        allOf(
            withId(com.example.design_system.R.id.inputEditText),
            isDescendantOfA(withId(R.id.chatNameInput))
        )
    }

    val createdChatsName: Matcher<View> by lazy {withText(Constants.FULL_NAME) }


    val createChatBttn: Matcher<View> by lazy {withId(R.id.createChatButton) }

    val createdChatsBttn: Matcher<View> by lazy {
        allOf(
            withId(com.example.design_system.R.id.listItemTextView),
            withText("შექმნილი ჩატები")
        )
    }
    val chatTitle: Matcher<View> by lazy { withId(com.example.design_system.R.id.chatTitleTextView)}
    val deleteChatBtn: Matcher<View> by lazy { withId(com.example.design_system.R.id.removeButton)}



}