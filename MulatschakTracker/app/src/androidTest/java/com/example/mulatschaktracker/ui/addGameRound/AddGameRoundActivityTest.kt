package com.example.mulatschaktracker.ui.addGameRound

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.mulatschaktracker.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class AddGameRoundActivityTest{

    @get: Rule
    val activityRule: ActivityScenarioRule<AddGameRoundActivity> =
        ActivityScenarioRule(AddGameRoundActivity::class.java)


    @Test
    fun test_isActivityInView() {
        Espresso.onView(withId(R.id.update_game_score))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun test_isButtonPlayer1Displayed() {
        Espresso.onView(withId(R.id.button_player_1))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isButtonPlayer2Displayed() {
        Espresso.onView(withId(R.id.button_player_2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isButtonPlayer3Displayed() {
        Espresso.onView(withId(R.id.button_player_3))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isButtonPlayer4Displayed() {
        Espresso.onView(withId(R.id.button_player_4))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }



    @Test
    fun is_tvPlayer1Displayed() {
        Espresso.onView(withId(R.id.tvPlayerOne))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun is_tvPlayer2Displayed() {
        Espresso.onView(withId(R.id.tvPlayerTwo))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun is_tvPlayer3Displayed() {
        Espresso.onView(withId(R.id.tvPlayerThree))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun is_tvPlayer4Displayed() {
        Espresso.onView(withId(R.id.tvPlayerFour))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }



    @Test
    fun is_isButtonEndroundDisplayed() {
        Espresso.onView(withId(R.id.endround))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }



    @Test
    fun test_button1ClickableAndIncrement() {
        Espresso.onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_1))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button2ClickableAndIncrement() {
        Espresso.onView(withId(R.id.button_player_2)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_2))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button3ClickableAndIncrement() {
        Espresso.onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_3))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button4ClickableAndIncrement() {
        Espresso.onView(withId(R.id.button_player_4)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_4))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }


    @Test
    fun test_button1LongClickableAndIncrementFail() {
        Espresso.onView(withId(R.id.button_player_1)).perform(ViewActions.longClick())
        Espresso.onView(withId(R.id.button_player_1))
            .check(ViewAssertions.matches(ViewMatchers.withText("0")))
    }

    @Test
    fun test_button2LongClickableAndIncrementFail() {
        Espresso.onView(withId(R.id.button_player_2)).perform(ViewActions.longClick())
        Espresso.onView(withId(R.id.button_player_2))
            .check(ViewAssertions.matches(ViewMatchers.withText("0")))
    }

    @Test
    fun test_button3LongClickableAndIncrementFail() {
        Espresso.onView(withId(R.id.button_player_3)).perform(ViewActions.longClick())
        Espresso.onView(withId(R.id.button_player_3))
            .check(ViewAssertions.matches(ViewMatchers.withText("0")))
    }

    @Test
    fun test_button4LongClickableAndIncrementFail() {
        Espresso.onView(withId(R.id.button_player_4)).perform(ViewActions.longClick())
        Espresso.onView(withId(R.id.button_player_4))
            .check(ViewAssertions.matches(ViewMatchers.withText("0")))
    }




    @Test
    fun test_button1LongClickableAndIncrement() {
        Espresso.onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_1)).perform(ViewActions.longClick())
        Espresso.onView(withId(R.id.button_player_1))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button2LongClickableAndIncrement() {
        Espresso.onView(withId(R.id.button_player_2)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_2)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_2)).perform(ViewActions.longClick())
        Espresso.onView(withId(R.id.button_player_2))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button3LongClickableAndIncrement() {
        Espresso.onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_3)).perform(ViewActions.longClick())
        Espresso.onView(withId(R.id.button_player_3))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button4LongClickableAndIncrement() {
        Espresso.onView(withId(R.id.button_player_4)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_4)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.button_player_4)).perform(ViewActions.longClick())
        Espresso.onView(withId(R.id.button_player_4))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }




}