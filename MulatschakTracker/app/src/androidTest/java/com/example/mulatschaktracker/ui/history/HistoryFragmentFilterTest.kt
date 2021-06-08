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

    /*
    @Test
    fun readGameFromDatabase(){
        var scorePlayer1 = 0
        var scorePlayer2 = 0
        var scorePlayer3 = 0
        var scorePlayer4 = 0
        var heartRound = 0
        var underDog = 0

        val scoreToSavePlayer1 = -1
        val scoreToSavePlayer2 = 0
        val scoreToSavePlayer3 = 1
        val scoreToSavePlayer4 = 2
        val heartRoundToSave = 1
        val underdogToSave = 2

        val gameObject = GameObject("Player 1", "Player 2", "Player 3", "Player 4")
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        val newGameId = repo.createGame(gameObject, 4)
        val gameObjectFromDb = repo.getGame(newGameId)

        val new_round = RoundObject(scoreToSavePlayer1, scoreToSavePlayer2, scoreToSavePlayer3, scoreToSavePlayer4, underdogToSave, heartRoundToSave)
        repo.enterNewRound(new_round, newGameId)

        val cursor = repo.getCursorRounds(newGameId)
        if (cursor.moveToFirst()) {
            scorePlayer1 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER1_TICKS))
            scorePlayer2 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER2_TICKS))
            scorePlayer3 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER3_TICKS))
            scorePlayer4 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER4_TICKS))
            heartRound = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_HEARTROUND))
            underDog = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_UNDERDOG))

        }

        assertEquals(scoreToSavePlayer1, scorePlayer1)
        assertEquals(scoreToSavePlayer2, scorePlayer2)
        assertEquals(scoreToSavePlayer3, scorePlayer3)
        assertEquals(scoreToSavePlayer4, scorePlayer4)
        assertEquals(heartRoundToSave, heartRound)
        assertEquals(underdogToSave, underDog)

    }

     */

    @Test
    fun finishedGameIsPresentInHistoryFragment(){
        onView(withId(R.id.game_textview)).check(matches(isDisplayed()))
    }





}