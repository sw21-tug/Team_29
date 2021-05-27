package com.example.mulatschaktracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

val TESTUSER = "NewUser"

@RunWith(AndroidJUnit4::class)
class DeleteUserTest : TestCase() {
    lateinit var scenario: ActivityScenario<MainActivity>
    lateinit var appContext: Context

    @Before
    public override fun setUp(){
        super.setUp()
        appContext = ApplicationProvider.getApplicationContext()
        val userRepo = UserRepository(appContext)
        userRepo.resetDatabase()
        userRepo.createUser(UserObject(TESTUSER))
        val preferences = appContext.getSharedPreferences(PREFERENCENAME, AppCompatActivity.MODE_PRIVATE)
        preferences.edit().putString(LASTUSER, TESTUSER).commit()
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
        onView(withText(R.string.delete_user_button))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
        onView(withText(R.string.alert_delete_user))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
    }

    @Test(expected = Exception::class)
    fun checkConfirmDialogClickYes(){
        onView(withId(R.id.navigation_Options)).perform(click())
        onView(withId(R.id.DeleteUserButton)).perform(click())
        onView(withText(R.string.yes))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
                .perform(click())
        val userRepo = UserRepository(appContext)
        val user = userRepo.getUser(TESTUSER)
        assert(false)
    }

    @Test
    fun checkConfirmDialogClickCancel(){
        onView(withId(R.id.navigation_Options)).perform(click())
        onView(withId(R.id.DeleteUserButton)).perform(click())
        onView(withText(R.string.cancel))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
                .perform(click())
        val userRepo = UserRepository(appContext)
        val user = userRepo.getUser(TESTUSER)
        assert(user.id > 0)
    }

    @Test
    fun checkConfirmDialogNavigate(){
        onView(withId(R.id.navigation_Options)).perform(click())
        onView(withId(R.id.DeleteUserButton)).perform(click())
        onView(withText(R.string.yes))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
                .perform(click())
        onView(withId(R.id.SubmitUserNameButton)).check(matches(isDisplayed()))
    }
}
