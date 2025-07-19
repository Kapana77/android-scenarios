package com.example.foroom.pagematchers

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import okhttp3.internal.userAgent
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`

object IndividualChatPageMatchers {
    val sentMessageContainer: Matcher<View> by lazy {
        allOf(
            withId(R.id.messageView),
            isDescendantOfA(withId(R.id.messagesRecyclerView)),

        )
    }


    val messageSent: Matcher<View> by lazy {
        allOf(
            withId(com.example.design_system.R.id.messageTextView),
            isDescendantOfA(sentMessageContainer)
        )
    }



    private fun user1(name: String): Matcher<View> {
        return allOf(
            withId(com.example.design_system.R.id.userNameTextView),
            withText(name)
        )
    }

    private fun messageContainerFromFirstUser(userName: String): Matcher<View> {
        return allOf(
            withId(com.example.design_system.R.id.contentLinearLayout),
            hasDescendant(user1(userName))
        )
    }


    fun messageSentFromFirstUser(userName: String): Matcher<View> {
        return allOf(
            withId(com.example.design_system.R.id.messageTextView),
            isDescendantOfA(messageContainerFromFirstUser(userName))
        )
    }



    fun layout(userName: String): Matcher<View> {
        return  allOf(
            withClassName(`is`("android.widget.LinearLayout")),
            hasDescendant(userName(userName))
        )
    }


    fun linearLayout(userName: String): Matcher<View> {
        return allOf(
            withId(com.example.design_system.R.id.contentLinearLayout),
            hasDescendant(layout(userName))
        )
    }

    fun messageFromFirstUser(userName: String): Matcher<View> {
        return allOf(
            withText("yoo whats up"),
            isDescendantOfA(linearLayout(userName))
        )
    }



    fun userName(userName: String): Matcher<View> {
        return allOf(
            withId(R.id.userNameTextView),
            withText(userName)
        )
    }






}