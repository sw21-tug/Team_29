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

@RunWith(AndroidJUnit4::class)
class GameRepositoryTest {

    @Test
    fun createNewGameInDatabase(){
        val gameObject = GameObject("Player 1","Player 2", "Player 3", "Player 3")
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        repo.resetDatabase()
        val newGameId = repo.createGame(gameObject, 1)
        assert(newGameId > 0)
    }

    @Test
    fun readGameFromDatabaseGameExists(){
        val gameObject = GameObject("Player 1","Player 2", "Player 3", "Player 4")
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        repo.resetDatabase()
        val newGameId = repo.createGame(gameObject, 1)
        val gameObjectFromDb = repo.getGame(newGameId)
        assertEquals(gameObjectFromDb.id, newGameId)
        assertEquals(gameObjectFromDb.player1, "Player 1")
        assertEquals(gameObjectFromDb.player2, "Player 2")
        assertEquals(gameObjectFromDb.player3, "Player 3")
        assertEquals(gameObjectFromDb.player4, "Player 4")

    }

    @Test(expected = Exception::class)
    fun readGameFromDatabaseGameDoesNotExist(){
        val gameObject = GameObject("Player 1","Player 2", "Player 3", "Player 4")
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        repo.resetDatabase()
        val gameObjectFromDb = repo.getGame(1)
        assert(false)

    }


    @Test
    fun getGamesPerUserFromDbUserAndGamesExist() {
        val appContext: Context = ApplicationProvider.getApplicationContext();

        //Test for the activity of starting a new game
        val userRepo = UserRepository(appContext)
        userRepo.resetDatabase()
        val gameRepo = GameRepository(appContext)
        val userID = userRepo.createUser(UserObject("NewUser"))

        val gameId1 = gameRepo.createGame(GameObject("Player 1", "Player 2", "Player 3", "Player 4"),userID)
        val gameId2 = gameRepo.createGame(GameObject("Player 1", "Player 2", "Player 3", "Player 4"),userID)
        val gameList = gameRepo.getGames(userID)
        assertEquals(gameId1,gameList[0].id)
        assertEquals(gameId2,gameList[1].id)
    }

    @Test
    fun getGamesPerUserFromDbNoGamesExist() {
        val appContext: Context = ApplicationProvider.getApplicationContext();

        //Test for the activity of starting a new game
        val userRepo = UserRepository(appContext)
        userRepo.resetDatabase()
        val gameRepo = GameRepository(appContext)
        val userID = userRepo.createUser(UserObject("NewUser"))

        val gameList = gameRepo.getGames(userID)
        assertEquals(0,gameList.size)
    }

    @Test
    fun getGamesPerUserFromDbUserDoesNotExist() {
        val appContext: Context = ApplicationProvider.getApplicationContext();

        //Test for the activity of starting a new game
        val userRepo = UserRepository(appContext)
        userRepo.resetDatabase()
        val gameRepo = GameRepository(appContext)
        val userID : Long = 1
        val gameList = gameRepo.getGames(userID)
        assertEquals(0,gameList.size)
    }



}