package com.example.mulatschaktracker

import android.view.View
import android.widget.TextView
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher


import org.hamcrest.core.StringStartsWith


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


@RunWith(AndroidJUnit4::class)
class StartNewGameTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun startNewGameActivityExecuted() {
        //Test for the activity of starting a new game
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withText(StringStartsWith("Enter Player Names"))).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun backButtonNavigatesToMainActivity() {
        //Test for checking whether the return buttons behaves correctly or not
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        pressBack()
        onView(withText("This is home Fragment")).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    //Test for the activity of starting a new record table
    fun startNewGameButtonShowsRecordTable() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withText("Record Table")).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun startNewGameExecuted() {
        //Test for the correct Player names

        onView(withText(StringStartsWith("Enter Player Names"))).check(ViewAssertions.matches(isDisplayed()))

        //storing the name of the player from start new game activity
        val tempName: ViewInteraction = onView(withId(R.id.Player1_EditText))
        var storeName = getText(tempName)

        onView(withId(R.id.StartNewGameButton)).perform(click())

        //storing the name of the player from record table activity
        val textView: ViewInteraction = onView(withId(R.id.Player1_TextView))
        var textViewName = getText(tempName)

        //check if equal
        assertEquals(storeName, textViewName)
    }

    //function for comparing 2 strings from textboxes
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
