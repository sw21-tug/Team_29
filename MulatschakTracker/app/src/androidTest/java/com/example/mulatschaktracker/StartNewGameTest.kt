package com.example.mulatschaktracker

//import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4


import org.hamcrest.CoreMatchers.endsWith
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
        onView(withText(StringStartsWith("Player 1"))).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun backButtonNavigatesToMainActivity(){
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        pressBack()
        onView(withText("This is home Fragment")).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun startNewGameButtonPresent() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        //Test for the activity of starting a new game
        onView(withText("Start New Game")).check(ViewAssertions.matches(withId(R.id.StartNewGameButton).text))
    }

    @Test
    fun startNewGameButtonReturnToHome() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withText("This is home Fragment")).check(ViewAssertions.matches(isDisplayed()))
    }
}
