package com.example.mulatschaktracker

import android.content.Context
import android.service.autofill.Validators.not
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
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

// SOME testCases die because the ui craches

@RunWith(AndroidJUnit4::class)
class GameFinishedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun user1With0Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())

        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }

    @Test
    fun user3With100Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..14) {
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())
            onView(withId(R.id.endround)).perform(click())
            onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        }
        onView(withId(R.id.button_player_1)).perform(ViewActions.click())

        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }

    @Test
    fun noOneWon() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())
        onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())
        onView(withId(R.id.Game_Finished)).check(doesNotExist())
    }


    @Test
    fun users2With100Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..14) {

            onView(withId(R.id.button_player_2)).perform(ViewActions.click())
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())

            onView(withId(R.id.endround)).perform(click())
            onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        }
        onView(withId(R.id.button_player_2)).perform(ViewActions.click())
        onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }

    @Test
    fun users1With100Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..14) {
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())
            onView(withId(R.id.button_player_2)).perform(ViewActions.click())
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())

            onView(withId(R.id.endround)).perform(click())
            onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        }
        onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        onView(withId(R.id.button_player_2)).perform(ViewActions.click())
        onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }


    @Test
    fun user2With0Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_2)).perform(ViewActions.click())
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())

        }
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }


    @Test
    fun user3With0Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())
            onView(withId(R.id.button_player_2)).perform(ViewActions.click())
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        }
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }

    @Test
    fun oneWinner() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())

        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        assert(false)
    }

    @Test
    fun twoWinners() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())

        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        assert(false)

    }

    @Test
    fun threeWinners() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())

        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        assert(false)

    }


}