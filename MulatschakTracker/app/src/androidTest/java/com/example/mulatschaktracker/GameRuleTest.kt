package com.example.mulatschaktracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mulatschaktracker.ui.addGameRound.AddGameRoundActivityTest.Companion.getText
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GameRuleTest : TestCase() {
    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    public override fun setUp(){
        super.setUp()
        val  appContext: Context = ApplicationProvider.getApplicationContext()
        val userRepo = UserRepository(appContext)
        userRepo.resetDatabase()
        userRepo.createUser(UserObject("NewUser"))
        val preferences = appContext.getSharedPreferences(PREFERENCENAME, AppCompatActivity.MODE_PRIVATE)
        preferences.edit().putString(LASTUSER, "NewUser").commit()
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    public override fun tearDown(){
        super.tearDown()
        scenario.close()
    }
    @Test
    fun checkBoxClicked()
    {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.GameMode)).perform(click())
        onView(withId(R.id.GameMode)).check(matches(isChecked()))
    }

    @Test
    fun checkBoxNotClicked()
    {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.GameMode)).check(matches(isNotChecked()))
    }

    @Test
    fun startingValues()
    {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.GameMode)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        val initialPoints = "15"
        assertEquals(initialPoints, getText(onView(withId(1))))
        assertEquals(initialPoints, getText(onView(withId(2))))
        assertEquals(initialPoints, getText(onView(withId(3))))
        assertEquals(initialPoints, getText(onView(withId(4))))
    }
    @Test
    fun points15Game()
    {
        startingValues()
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        val gameRound = RoundObject(14, 0, 0, 0, 0, 0)
        repo.enterNewRound(gameRound, 1)

        onView(withId(R.id.AddRoundButton)).perform(click())
        onView(withId(R.id.button_player_1)).perform(click())
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }

}
