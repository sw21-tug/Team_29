package com.example.mulatschaktracker

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
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
import junit.framework.TestCase
import org.hamcrest.Matcher


import org.hamcrest.core.StringStartsWith
import org.junit.After


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


@RunWith(AndroidJUnit4::class)
class StartNewGameTest : TestCase() {

    lateinit var scenario : ActivityScenario<MainActivity>
    val username: String = "NewUser"

    @Before
    public override fun setUp(){

        super.setUp()
        val  appContext: Context = ApplicationProvider.getApplicationContext();
        val userRepo = UserRepository(appContext)
        userRepo.resetDatabase()
        userRepo.createUser(UserObject(username))
        val preferences = appContext.getSharedPreferences(PREFERENCENAME, AppCompatActivity.MODE_PRIVATE)
        preferences.edit().putString(LASTUSER, username).commit()
        scenario = ActivityScenario.launch(MainActivity::class.java)

    }

    @After
    public override fun tearDown() {
        super.tearDown()
        scenario.close()
    }

//    @get:Rule
//    var activityRule: ActivityScenarioRule<MainActivity>
//            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun startNewGameActivityExecuted() {
        //Test for the activity of starting a new game
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withText(StringStartsWith("Enter Player Names"))).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun backButtonNavigatesToMainActivity() {
        //Test for checking whether the return buttons behaves correctly or not
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        pressBack()
        onView(withText("No Running Games")).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun startNewGameActivityCanEnterPlayerNames() {
        //Test for the correct Player names
        val testString1 = "Test Player 1"
        val testString2 = "Test Player 2"
        val testString3 = "Test Player 3"
        val testString4 = "Test Player 4"

        // enter new game activity
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())

        // entering test strings into player name fields
        onView(withId(R.id.Player1_EditText))
                .perform(typeText(testString1), closeSoftKeyboard())
                .check(matches(isDisplayed()))
        onView(withId(R.id.Player2_EditText))
                .perform(typeText(testString2), closeSoftKeyboard())
                .check(matches(isDisplayed()))
        onView(withId(R.id.Player3_EditText))
                .perform(typeText(testString3), closeSoftKeyboard())
                .check(matches(isDisplayed()))
        onView(withId(R.id.Player4_EditText))
                .perform(typeText(testString4), closeSoftKeyboard())
                .check(matches(isDisplayed()))

    }

    @Test
    //Test for the activity of starting a new record table
    fun startNewGameButtonShowsGameTable() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withText(username)).check(matches(isDisplayed()))
    }



    @Test
    fun fetchingPlayerNamesFromDB(){

        onView(withId(R.id.StartNewGameActivityButton)).perform(click())

        val testString1 = "Test Player 1"
        val testString2 = "Test Player 2"
        val testString3 = "Test Player 3"
        val testString4 = "Test Player 4"

        onView(withId(R.id.Player1_EditText))
                .perform(typeText(testString1), closeSoftKeyboard())
        onView(withId(R.id.Player2_EditText))
                .perform(typeText(testString2), closeSoftKeyboard())
        onView(withId(R.id.Player3_EditText))
                .perform(typeText(testString3), closeSoftKeyboard())
        onView(withId(R.id.Player4_EditText))
                .perform(typeText(testString4), closeSoftKeyboard())

        onView(withId(R.id.StartNewGameButton)).perform(click())

        assertEquals(testString1, getText(onView(withId(R.id.textViewPlayer1))))
        assertEquals(testString2, getText(onView(withId(R.id.textViewPlayer2))))
        assertEquals(testString3, getText(onView(withId(R.id.textViewPlayer3))))
        assertEquals(testString4, getText(onView(withId(R.id.textViewPlayer4))))
    }

    @Test
    fun gameStartsWithDefaultNames() {
        val testString1 = username
        val testString2 = "Player 2"
        val testString3 = "Player 3"
        val testString4 = "Player 4"

        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())

        assertEquals(testString1, getText(onView(withId(R.id.textViewPlayer1))))
        assertEquals(testString2, getText(onView(withId(R.id.textViewPlayer2))))
        assertEquals(testString3, getText(onView(withId(R.id.textViewPlayer3))))
        assertEquals(testString4, getText(onView(withId(R.id.textViewPlayer4))))
    }

    @Test
    fun initialPointsAreDisplayed() {

        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())

        val initialPoints = "21"

        assertEquals(initialPoints, getText(onView(withId(1))))
        assertEquals(initialPoints, getText(onView(withId(2))))
        assertEquals(initialPoints, getText(onView(withId(3))))
        assertEquals(initialPoints, getText(onView(withId(4))))
    }


    //helper function for comparing 2 strings from textboxes
    fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })

        return text
    }
}
