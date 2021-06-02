package com.example.mulatschaktracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
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
    val userName = "TestUser"

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
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        assertEquals(userName, getText(onView(withId(R.id.Player1_EditText))))
        onView(withId(R.id.StartNewGameButton)).perform(click())
        assertEquals(userName, getText(onView(withId(R.id.textViewPlayer1))))
        onView(withId(R.id.AddRoundButton)).perform(click())
        assertEquals(userName, getText(onView(withId(R.id.tvPlayerOne))))
    }

    @Test
    fun checkUserNameDisappearsAfterOnClick(){
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        assertEquals(userName, getText(onView(withId(R.id.Player1_EditText))))
        onView(withId(R.id.Player1_EditText)).perform(click())
        assertEquals("", getText(onView(withId(R.id.Player1_EditText))))
    }

    @Test
    fun checkUserNameSetAsHint(){
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        assertEquals(userName, getText(onView(withId(R.id.Player1_EditText))))
        onView(withId(R.id.Player1_EditText)).perform(click())
        onView(withId(R.id.Player1_EditText)).check(matches(withHint(userName)))
        onView(withId(R.id.Player2_EditText)).perform(click())
        onView(withId(R.id.Player1_EditText)).check(matches(withHint(userName)))
    }

    @Test
    fun checkUserNameSetInGameFromEmptyField(){
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        assertEquals(userName, getText(onView(withId(R.id.Player1_EditText))))
        onView(withId(R.id.Player1_EditText)).perform(click())
        onView(withId(R.id.Player1_EditText)).check(matches(withHint(userName)))
        pressBack()
        onView(withId(R.id.StartNewGameButton)).perform(click())
        assertEquals(userName, getText(onView(withId(R.id.textViewPlayer1))))
    }
}