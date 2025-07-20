import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParentIndex
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.Helper.withIndex
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher

object LoginPageMatchers {
    val userNameContainer: Matcher<View> by lazy {
        allOf(
            withId(R.id.userNameInput),
            hasSibling(withId(R.id.logInButton)),
            hasSibling(withId(R.id.signUpButton)),
            not(hasSibling(withId(R.id.repeatPasswordInput))),
        )
    }

    val usernameField: Matcher<View> by lazy {
        allOf(
            withHint("მომხმარებლის სახელი")
        )
    }



    val passwordField: Matcher<View> by lazy { withHint("პაროლი") }
    val loginButton: Matcher<View> by lazy { withId(R.id.logInButton) }

    val registerButton: Matcher<View> by lazy { withId(R.id.signUpButton) }



}