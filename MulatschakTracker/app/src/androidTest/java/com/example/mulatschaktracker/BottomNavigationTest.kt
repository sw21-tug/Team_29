package com.example.mulatschaktracker

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import junit.framework.TestCase
import org.junit.Test
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.ext.junit.rules.ActivityScenarioRule

@RunWith(AndroidJUnit4::class)
class BottomNavigationTest : TestCase() {

    public override fun setUp() {
        super.setUp()
    }

    public override fun tearDown() {}
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
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
        //onView(withId(R.id.navigation_history)).perform(click())
        onView(withId(R.id.text_statistic)).check(matches(isDisplayed()))
    }

    @Test
    fun checkTippsWindow() {

        onView(withId(R.id.navigation_Tipps)).perform(click())

       // onView(withId(R.id.navigation_tipps)).perform(click())
        onView(withId(R.id.text_tipps)).check(matches(isDisplayed()))
    }

    @Test
    fun checkOptionsWindow() {
        onView(withId(R.id.navigation_Options)).perform(click())
      //  onView(withId(R.id.navigation_options)).perform(click())
        onView(withId(R.id.text_options)).check(matches(isDisplayed()))
    }   

}