package com.example.mulatschaktracker

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase

import org.junit.After

import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class HistoryFragmentFilterTest:TestCase() {
    lateinit var userRepo : UserRepository
    lateinit var gameRepo : GameRepository
    lateinit var scenario : ActivityScenario<MainActivity>

    val userName = "NewUser"
    val playerName = "PlayerName"

    @Before
    public override fun setUp(){
        super.setUp()
        val  appContext: Context = ApplicationProvider.getApplicationContext()

        userRepo = UserRepository(appContext)
        userRepo.resetDatabase()

        val userID = userRepo.createUser(UserObject(userName))
        gameRepo = GameRepository(appContext)

        createFinishesGame(userID,16,1,0,1,1, -1)    // player two 100
        createFinishesGame(userID,16,1,1,1,0, -1)    // player four 100
        createFinishesGame(userID,16,-1,-1,0,1, -1)    // player three 100
        createFinishesGame(userID,11,2,1,1,1, 1)    // player one won
        createFinishesGame(userID,8,3,1,1,1, 1)    // player one won
        createFinishesGame(userID,8,3,1,1,1, 1)    // player one won
        createFinishesGame(userID,8,-1,1,2,3, 0)    // player one last
        createFinishesGame(userID,8,-1,1,2,3, 0)    // player one last
        createFinishesGame(userID,8,-1,1,2,3, 0)    // player one last
        createFinishesGame(userID,16,0,-1,-1,-1, -2)    // player one last with 100
        createFinishesGame(userID,16,0,-1,-1,-1, -2)    // player one last with 100
        createFinishesGame(userID,16,0,-1,-1,-1, -2)    // player one last with 100



        val preferences = appContext.getSharedPreferences(PREFERENCENAME, AppCompatActivity.MODE_PRIVATE)
        preferences.edit().putString(LASTUSER, userName).commit()
        scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.navigation_History)).perform(click())

    }

    @After
    public override fun tearDown() {
        super.tearDown()
        scenario.close()
    }

    fun createFinishesGame(uID : Long, rounds: Int, p1:Int, p2:Int, p3:Int, p4:Int, winner:Int){
        val gameObject = GameObject(playerName,playerName,playerName,playerName)
        gameObject.winner = winner
        gameObject.finished = 1
        val newGameId =  gameRepo.createGame(gameObject, uID)

        for (i in 0..rounds) {
            val new_round = RoundObject(p1, p2, p3, p4, 0, 0)
            gameRepo.enterNewRound(new_round, newGameId)
        }
    }



    @Test
    fun test_playerOneOneRadioGroupDisplayed(){
        onView(withId(R.id.radioGroup_filter)).check(matches(isDisplayed()))
        onView(withId(R.id.radio_won)).check(matches(isDisplayed()))
        onView(withId(R.id.radio_lost)).check(matches(isDisplayed()))
        onView(withId(R.id.radio_100)).check(matches(isDisplayed()))
    }

    




}