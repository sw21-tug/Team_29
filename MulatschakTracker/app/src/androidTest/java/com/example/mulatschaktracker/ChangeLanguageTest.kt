package com.example.mulatschaktracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.test.InstrumentationRegistry
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.*

import com.example.mulatschaktracker.ui.createUser.CreateUserActivity
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChangeLanguageTest : TestCase() {
    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    public override fun setUp() {
        super.setUp()
        InstrumentationRegistry.getTargetContext().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit().clear().commit()

        val  appContext: Context = ApplicationProvider.getApplicationContext();
        val userRepo = UserRepository(appContext)
        userRepo.resetDatabase()
        userRepo.createUser(UserObject("NewUser"))
        val preferences = appContext.getSharedPreferences(PREFERENCENAME, AppCompatActivity.MODE_PRIVATE)
        preferences.edit().putString(LASTUSER, "NewUser").commit()
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    public override fun tearDown() {
        super.tearDown()
        scenario.close()
    }
    //@get:Rule
    //val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun UserOptionsInRightLanguageTetst(){

        onView(withId(R.id.navigation_Options)).perform(click())
        //  onView(withId(R.id.navigation_options)).perform(click())
        onView(withId(R.id.text_options)).check(matches(isDisplayed()))

        onView(withId(R.id.buttonChangeLanguage)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonChangeLanguage)).check(matches(withText("Choose Language")));

        onView(withId(R.id.buttonChangeLanguage)).perform(click())
        onView(withText("Русский"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.buttonChangeLanguage)).check(matches(withText("Выберите язык")));
        onView(withId(R.id.buttonChangeLanguage)).perform(click())
        onView(withText("English"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.buttonChangeLanguage)).check(matches(withText("Choose Language")));



    }



}
