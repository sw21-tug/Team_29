package com.example.mulatschaktracker

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mulatschaktracker.ui.addGameRound.AddGameRoundActivityTest
import junit.framework.TestCase
import org.hamcrest.Matcher


import org.hamcrest.core.StringStartsWith
import org.junit.After


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class GameFragmentTest:TestCase() {
    lateinit var userRepo : UserRepository
    lateinit var gameRepo : GameRepository
    lateinit var scenario : ActivityScenario<MainActivity>
    val userName = "NewUser"
    val playerName = "PlayerName"
    //@get:Rule
    //var activityRule: ActivityScenarioRule<MainActivity>
    //        = ActivityScenarioRule(MainActivity::class.java)

    @Before
    public override fun setUp(){
        super.setUp()
        val  appContext: Context = ApplicationProvider.getApplicationContext()

        userRepo = UserRepository(appContext)
        userRepo.resetDatabase()

        val userID = userRepo.createUser(UserObject(userName))
        gameRepo = GameRepository(appContext)
        gameRepo.createGame(GameObject(playerName,playerName,playerName,playerName),userID)

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
    fun newGameIsPresentInGameFragment(){
        onView(withId(R.id.game_textview)).check(matches(isDisplayed()))
    }

    @Test
    fun correctGameIdDisplayedInGameFragment(){
        assertEquals(getText(onView(withId(R.id.game_textview))),"Game 1")
    }

    @Test
    fun correctPlayerNamesAreDisplayedInGameFragment(){
        assertEquals(getText(onView(withId(R.id.game_textview_players))),
            "PlayerName, PlayerName, PlayerName, PlayerName"
        )
    }

    @Test
    fun clickOnGameOpensGameActivity(){
        onView(withId(R.id.gamesRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        assertEquals(playerName, getText(onView(withId(R.id.textViewPlayer1))))
        assertEquals(playerName, getText(onView(withId(R.id.textViewPlayer2))))
        assertEquals(playerName, getText(onView(withId(R.id.textViewPlayer3))))
        assertEquals(playerName, getText(onView(withId(R.id.textViewPlayer4))))
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


    @Test
    //Test if the backbutton works correctly on GameScreen
    fun checkBackButtonFromGameScreen() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        pressBack();
        onView(withId(R.id.StartNewGameActivityButton)).check(matches(isDisplayed()))
    }
    @Test
    //Test if the backbutton works correctly on Edit screen
    fun checkBackButtonFromEditGameScreen() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())
        pressBack();
        onView(withId(R.id.AddRoundButton)).check(matches(isDisplayed()))
    }

    @Test
    //Test if the backbutton works correctly when leaving Edit screen
    fun checkBackButtonAfterEditGameScreen() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())
        onView(withId(R.id.button_player_1)).perform(click())
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())
        onView(withId(R.id.button_player_2)).perform(click())
        onView(withId(R.id.endround)).perform(click())
        pressBack();
        onView(withId(R.id.StartNewGameActivityButton)).check(matches(isDisplayed()))
    }

    @Test
    fun checkReplacingFiveByMuliForPlayer1() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())
        for (i in 0..4){
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        }
        assertEquals("MULI", getText(onView(withId(R.id.button_player_1))))
    }

    @Test
    fun checkDoublePointsForPlayer1() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())
        for (i in 0..4){
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        }
        onView(withId(R.id.endround)).perform(click())
        assertEquals("11", AddGameRoundActivityTest.getText(onView(withId(5))))
        assertEquals("26", AddGameRoundActivityTest.getText(onView(withId(6))))
        assertEquals("26", AddGameRoundActivityTest.getText(onView(withId(7))))
        assertEquals("26", AddGameRoundActivityTest.getText(onView(withId(8))))
    }

    @Test
    fun checkNoAdditionAfterMuliForOtherPlayers(){
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())
        for (i in 0..4){
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        }
        onView(withId(R.id.button_player_2)).perform(ViewActions.click())
        onView(withId(R.id.button_player_2))
                .check(ViewAssertions.matches(ViewMatchers.withText("0")))
    }

    @Test
    fun checkNoAdditionAfterMuliForMuliPlayer(){
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())
        for (i in 0..4){
            onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        }
        onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        assertEquals("MULI", getText(onView(withId(R.id.button_player_1))))
    }
}
