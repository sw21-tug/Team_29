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
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeleteUserTest : TestCase() {
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
    fun deleteButtonPresent(){
        onView(withId(R.id.navigation_Options)).perform(click())
        onView(withId(R.id.DeleteUserButton)).check(matches(isDisplayed()))
    }

    @Test
    fun checkConfirmDialog(){
        onView(withId(R.id.navigation_Options)).perform(click())
        onView(withId(R.id.DeleteUserButton)).perform(click())
       /* onView(withId(R.id.ConfirmDialog)).check(matches(isDisplayed()))
        onView(withId(R.id.ConfirmDialog)).check(matches(withText("Are you sure?"))) */
    }
}
