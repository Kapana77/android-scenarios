package com.example.foroom.steps

import com.example.foroom.Helper.getText
import com.example.foroom.Helper.tap
import com.example.foroom.data.Constants
import com.example.foroom.pagematchers.LanguagePageMatchers
import org.junit.Assert

class LanguageSteps {
    fun tapChangeLanguage(): LanguageSteps {
        with(LanguagePageMatchers) {
            changeLanguageButton.tap()
        }
        return this;
    }

    fun chooseEnglishLangauge(): LanguageSteps{
        with(LanguagePageMatchers){
            englishLanguageButton.tap()
        }
        return this;
    }

    fun chooseGeorgianLangauge(): LanguageSteps{
        with(LanguagePageMatchers){
            georgianLanguageButton.tap()
        }
        return this;
    }

    fun validateLanguageChangedToEnglish(): LanguageSteps{
        with(LanguagePageMatchers){
//            onView(createdChatsToValidateLang).waitUntilVisible(3)
            Assert.assertEquals(createdChatsToValidateLang.getText(),Constants.ENGLISH_TEXT)
        }
        return this;
    }
    fun validateLanguageChangedToGeorgian(): LanguageSteps{
        with(LanguagePageMatchers){
            Assert.assertEquals(createdChatsToValidateLang.getText(),Constants.GEORGIAN_TEXT)

        }
        return this;
    }

}