package com.example.foroom.pagematchers


import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import org.hamcrest.Matcher

object HomePageMatchers {
    val profileNavButton: Matcher<View> by lazy {
        withId(R.id.homeNavigationProfile)
    }

    val createChatBttn: Matcher<View> by lazy { withId(R.id.homeNavigationCreateChat)}
}