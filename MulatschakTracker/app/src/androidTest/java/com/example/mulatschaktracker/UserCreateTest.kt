package com.example.mulatschaktracker

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mulatschaktracker.ui.createUser.CreateUserActivity
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserCreateTest : TestCase() {

    lateinit var repo : UserRepository;

    @Before
    public override fun setUp() {
        super.setUp()

        val  appContext: Context = ApplicationProvider.getApplicationContext();
        repo = UserRepository(appContext);
        repo.resetDatabase();
    }

    val validUserName = "User1";
    val invalidUserName = "";

    @After
    public override fun tearDown() {}
    @get:Rule
    val activityRule = ActivityScenarioRule(CreateUserActivity::class.java)

    @Test
    fun ValidUserNameTest(){

        onView(withId(R.id.UserNameInput)).perform(typeText(validUserName))
        closeSoftKeyboard()
        onView(withId(R.id.SubmitUserNameButton)).perform(click());

        val  user = repo.getUser(validUserName);

        assert(user.name.equals(validUserName));
        assert(user.id >= 0);
    }

    @Test
    fun InvalidUserNameTest(){

        onView(withId(R.id.UserNameInput)).perform(typeText(invalidUserName));
        closeSoftKeyboard()
        onView(withId(R.id.SubmitUserNameButton)).perform(click());

        onView(withText(USERNAME_EMPTY_MESSAGE)).check(matches(isDisplayed()));
    }

    @Test
    fun UserNameInUseTest(){

        val user = UserObject(validUserName)
        repo.createUser(user)
        onView(withId(R.id.UserNameInput)).perform(typeText(validUserName));
        closeSoftKeyboard()
        onView(withId(R.id.SubmitUserNameButton)).perform(click());

        onView(withText(USERNAME_TAKEN_MESSAGE)).check(matches(isDisplayed()));

    }

    @Test
    fun userNameDisplayedInActionBar(){
        onView(withId(R.id.UserNameInput)).perform(typeText(validUserName))
        closeSoftKeyboard()
        onView(withId(R.id.SubmitUserNameButton)).perform(click())
        assertEquals(getText(onView(withId(R.id.userNameLabel))),validUserName)
    }

    @Test
    fun userNameDisplayedInActionBarFragmentSwitched(){
        onView(withId(R.id.UserNameInput)).perform(typeText(validUserName))
        closeSoftKeyboard()
        onView(withId(R.id.SubmitUserNameButton)).perform(click())
        onView(withId(R.id.navigation_History)).perform(click())
        assertEquals(getText(onView(withId(R.id.userNameLabel))),validUserName)
        //onView(withId(R.id.navigation_statistic)).perform(click())
        //assertEquals(getText(onView(withId(R.id.userNameLabel))),validUserName)
        onView(withId(R.id.navigation_Options)).perform(click())
        assertEquals(getText(onView(withId(R.id.userNameLabel))),validUserName)
    }


    //helper function for comparing 2 strings from textboxes
    fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })

        return text
    }

}
