package com.example.mulatschaktracker.ui.editGameRound

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.mulatschaktracker.MainActivity
import com.example.mulatschaktracker.R
import com.example.mulatschaktracker.ui.addGameRound.AddGameRoundActivityTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class EditGameRoundActivityTest {

    @get: Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Setup test data
     */
    @Before
    fun setupTests(){
        Espresso.onView(ViewMatchers.withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.EndGameButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_1)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_1)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_2)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_4)).perform(ViewActions.longClick())
        Espresso.onView(ViewMatchers.withId(R.id.endround)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.EndGameButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_2)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_2)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_3)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_4)).perform(ViewActions.longClick())
        Espresso.onView(ViewMatchers.withId(R.id.endround)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.EndGameButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_1)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_2)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_4)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_4)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.endround)).perform(ViewActions.click())
    }

    /**
     * check all rows at once since setup of test data is expensive
     */
    @Test
    fun performLongClickOnRow() {
        Espresso.onView(ViewMatchers.withId(12000)).perform(ViewActions.longClick())
        checkEditScreen("2", "1", "0", "LEFT")
        Espresso.onView(ViewMatchers.withId(R.id.endround)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(12001)).perform(ViewActions.longClick())
        checkEditScreen("0", "2", "1", "LEFT")
        Espresso.onView(ViewMatchers.withId(R.id.endround)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(12002)).perform(ViewActions.longClick())
        checkEditScreen("1", "1", "0", "2")
    }

    fun checkEditScreen(b1:String, b2:String, b3:String, b4:String) {
        Espresso.onView(ViewMatchers.withId(R.id.button_player_1))
            .check(ViewAssertions.matches(ViewMatchers.withText(b1)))
        Espresso.onView(ViewMatchers.withId(R.id.button_player_2))
            .check(ViewAssertions.matches(ViewMatchers.withText(b2)))
        Espresso.onView(ViewMatchers.withId(R.id.button_player_3))
            .check(ViewAssertions.matches(ViewMatchers.withText(b3)))
        Espresso.onView(ViewMatchers.withId(R.id.button_player_4))
            .check(ViewAssertions.matches(ViewMatchers.withText(b4)))
    }

}
