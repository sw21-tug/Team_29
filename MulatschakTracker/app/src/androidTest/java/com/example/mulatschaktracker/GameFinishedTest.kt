package com.example.mulatschaktracker

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
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
class GameFinishedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun userWith0Points(){

        val gameObject = GameObject("Player 1","Player 2", "Player 3", "Player 3")
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        val newGameId = repo.createGame(gameObject)
        var roundObject = RoundObject(1,20,0,60)
        //var winnerList:List<String> = listOf()
        onView(withId(R.id.EndGameButton)).perform(click())
        for (i in roundObject.pointList.indices){
            if (i < 1 || i >= 100 )
            {
             //   onView(withId(R.id.GameFinished)).check(matches(isDisplayed()))
                return
            }
        }
    }


    @Test
    fun userWith100Points(){

        val gameObject = GameObject("Player 1","Player 2", "Player 3", "Player 3")
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        val newGameId = repo.createGame(gameObject)
        var roundObject = RoundObject(1,20,0,100)
        //var winnerList:List<String> = listOf()
        onView(withId(R.id.EndGameButton)).perform(click())
        for (i in roundObject.pointList.indices){
            if (i < 1 || i >= 100 )
            {
               // onView(withId(R.id.GameFinished)).check(matches(isDisplayed()))
                return
            }
        }
    }
    @Test
    fun multipleWinners(){

        val gameObject = GameObject("Player 1","Player 2", "Player 3", "Player 3")
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        val newGameId = repo.createGame(gameObject)
        var roundObject = RoundObject(0,20,0,100)
        //var winnerList:List<String> = listOf()
        onView(withId(R.id.EndGameButton)).perform(click())
        for (i in roundObject.pointList.indices){
            if (i < 1 || i >= 100 )
            {
              //  onView(withId(R.id.GameFinished)).check(matches(isDisplayed()))
                return
            }
        }
    }

    @Test
    fun nooonewon(){

        val gameObject = GameObject("Player 1","Player 2", "Player 3", "Player 3")
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        val newGameId = repo.createGame(gameObject)
        var roundObject = RoundObject(1,20,3,100)
        onView(withId(R.id.EndGameButton)).perform(click())
        for (i in roundObject.pointList.indices){
            if (i < 1 || i >= 100 )
            {
              //  onView(withId(R.id.GameFinished)).check(matches(isDisplayed()))
                return
            }
        }
    }


    }
