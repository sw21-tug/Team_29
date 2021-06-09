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
class eBottomNavigationTest : TestCase() {
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
    fun checkHomeWindow(){

        onView(withId(R.id.navigation_home)).perform(click())

        onView(withId(R.id.text_game)).check(matches(isDisplayed()))
    }

    @Test
    fun checkDashboardWindow(){
        onView(withId(R.id.navigation_History)).perform(click())
        onView(withId(R.id.text_history)).check(matches(isDisplayed()))
       // onView(withId(R.id.navigation_dashboard)).perform(click())
     //   onView(withId(R.id.text_notifications)).check(matches(isDisplayed()))
    }

    @Test
    fun checkHistoryWindow() {
        onView(withId(R.id.navigation_statistic)).perform(click())
        //onView(withId(R.id.text_statistic)).check(matches(isNotDisplayed()))
    }

    @Test
    fun checkOptionsWindow() {
        onView(withId(R.id.navigation_Options)).perform(click())
      //  onView(withId(R.id.navigation_options)).perform(click())
        onView(withId(R.id.text_options)).check(matches(isDisplayed()))
    }   

}
