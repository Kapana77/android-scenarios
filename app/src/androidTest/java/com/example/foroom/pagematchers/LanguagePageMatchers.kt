package com.example.foroom.pagematchers

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object LanguagePageMatchers {
    val changeLanguageButton: Matcher<View> by lazy { withId(R.id.changeLanguageItem) }
    val englishLanguageButton: Matcher<View> by lazy { withId(R.id.languageButtonEng) }
    val createdChatsToValidateLang: Matcher<View> by lazy {
        allOf(
            isDescendantOfA(withId(R.id.createdChatsItem)),
            withId(com.example.design_system.R.id.listItemTextView)
        )
    }
    val georgianLanguageButton: Matcher<View> by lazy { withId(R.id.languageButtonGeo) }

}