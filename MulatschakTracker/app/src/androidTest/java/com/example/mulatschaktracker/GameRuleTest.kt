package com.example.mulatschaktracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import junit.framework.TestCase
import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class GameRuleTest : TestCase() {
    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    public override fun setUp(){
        super.setUp()
        val  appContext: Context = ApplicationProvider.getApplicationContext();
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
        onView(withId(R.id.GameMode)).perform(click());
        onView(withId(R.id.GameMode)).check(matches(isChecked()))
    }

    @Test
    fun checkBoxNotClicked()
    {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.GameMode)).check(matches(isNotChecked()))
    }

}