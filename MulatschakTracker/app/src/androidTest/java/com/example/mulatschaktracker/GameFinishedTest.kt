package com.example.mulatschaktracker


import androidx.test.espresso.Espresso.onView

import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import org.hamcrest.CoreMatchers.allOf

import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4



import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.EnumSet.allOf


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


        onView(allOf(withId(R.id.Game_Finished),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
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
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())

        }
        onView(withId(R.id.endround)).perform(click())

        onView(allOf(withId(R.id.Game_Finished),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        onView(allOf(withId(R.id.textView),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))


        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 ")))
    }

    @Test
    fun twoWinners() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 Player 3 ")))
       // assert(false)

    }

    @Test
    fun threeWinners() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())
            onView(withId(R.id.button_player_2)).perform(ViewActions.click())

        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 Player 2 Player 3 ")))

    }

    @Test
    fun nameforthewinnders() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())
            onView(withId(R.id.button_player_2)).perform(ViewActions.click())
            onView(withId(R.id.button_player_4)).perform(ViewActions.click())


        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 Player 2 Player 3 Player 4 ")))

    }

    @Test
    fun normalgame() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())


        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 ")))
        onView(withId(R.id.textView2)).check(matches(withText("2. Place Player 2")))
        onView(withId(R.id.textView3)).check(matches(withText("3. Place Player 3")))
        onView(withId(R.id.textView4)).check(matches(withText("4. Place Player 4")))


    }

    @Test
    fun p1324() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())

        }
        onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 ")))
        onView(withId(R.id.textView2)).check(matches(withText("2. Place Player 3")))
        onView(withId(R.id.textView3)).check(matches(withText("3. Place Player 2")))
        onView(withId(R.id.textView4)).check(matches(withText("4. Place Player 4")))
    }




   /*/ fun name100() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        for (i in 0..20) {
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())
            onView(withId(R.id.button_player_2)).perform(ViewActions.click())
            onView(withId(R.id.button_player_3)).perform(ViewActions.click())

            onView(withId(R.id.endround)).perform(click())
            onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        }
        onView(withId(R.id.button_player_1)).perform(ViewActions.click())

        onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 ")))
        onView(withId(R.id.textView2)).check(matches(withText("2. Place Player 3")))
        onView(withId(R.id.textView3)).check(matches(withText("3. Place Player 2")))
        onView(withId(R.id.textView4)).check(matches(withText("4. Place Player 4")))
    }*/

    @Test
    fun name3100() {
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
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 Player 2 Player 3 ")))
        onView(withId(R.id.textView2)).check(matches(withText("2. Place Player 4")))
    }

}