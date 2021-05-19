package com.example.mulatschaktracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mulatschaktracker.ui.addGameRound.AddGameRoundActivityTest.Companion.getText
import junit.framework.TestCase

import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class CheckUserNameFirstTest:TestCase(){
    lateinit var scenario : ActivityScenario<MainActivity>
    val userName = "NewUser"

    @Before
    public override fun setUp(){
        super.setUp()
        val  appContext: Context = ApplicationProvider.getApplicationContext();
        val userRepo = UserRepository(appContext)
        userRepo.resetDatabase()
        userRepo.createUser(UserObject(userName))
        val preferences = appContext.getSharedPreferences(PREFERENCENAME, AppCompatActivity.MODE_PRIVATE)
        preferences.edit().putString(LASTUSER, userName).commit()
        scenario = ActivityScenario.launch(MainActivity::class.java)

    }

    @After
    public override fun tearDown() {
        super.tearDown()
        scenario.close()
    }

    @Test
    fun checkUserNameAsPlayerOne() {
        Espresso.onView(ViewMatchers.withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        assertEquals(userName, getText(Espresso.onView(ViewMatchers.withId(R.id.Player1_EditText))))
        Espresso.onView(ViewMatchers.withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        assertEquals(userName, getText(Espresso.onView(ViewMatchers.withId(R.id.textViewPlayer1))))
    }
}