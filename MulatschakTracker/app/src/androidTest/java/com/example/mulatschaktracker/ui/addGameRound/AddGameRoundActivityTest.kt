package com.example.mulatschaktracker.ui.addGameRound
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.mulatschaktracker.*
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.properties.Delegates


@RunWith(AndroidJUnit4ClassRunner::class)
class AddGameRoundActivityTest : TestCase(){
    var userID : Long = 0

    @Before
    public override fun setUp(){
        super.setUp()
        val  appContext: Context = ApplicationProvider.getApplicationContext();
        val userRepo = UserRepository(appContext)
        userRepo.resetDatabase()
        userID = userRepo.createUser(UserObject("NewUser"))
        val preferences = appContext.getSharedPreferences(PREFERENCENAME, AppCompatActivity.MODE_PRIVATE)
        preferences.edit().putString(LASTUSER, "NewUser").commit()

    }

    @get: Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isActivityInView() {
        onView(ViewMatchers.withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())


        onView(withId(R.id.update_game_score))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun test_isButtonPlayer1Displayed() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_1))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isButtonPlayer2Displayed() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isButtonPlayer3Displayed() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_3))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isButtonPlayer4Displayed() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_4))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun is_tvPlayer1Displayed() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.tvPlayerOne))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun is_tvPlayer2Displayed() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.tvPlayerTwo))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun is_tvPlayer3Displayed() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.tvPlayerThree))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun is_tvPlayer4Displayed() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.tvPlayerFour))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun is_tvPlayerRightName() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())

        val testString1 = "Test Player 1"
        val testString2 = "Test Player 2"
        val testString3 = "Test Player 3"
        val testString4 = "Test Player 4"

        onView(withId(R.id.Player1_EditText))
            .perform(ViewActions.typeText(testString1), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.Player2_EditText))
            .perform(ViewActions.typeText(testString2), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.Player3_EditText))
            .perform(ViewActions.typeText(testString3), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.Player4_EditText))
            .perform(ViewActions.typeText(testString4), ViewActions.closeSoftKeyboard())



        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())



        assertEquals(testString1, getText(onView(withId(R.id.tvPlayerOne))))
        assertEquals(testString2, getText(onView(withId(R.id.tvPlayerTwo))))
        assertEquals(testString3, getText(onView(withId(R.id.tvPlayerThree))))
        assertEquals(testString4, getText(onView(withId(R.id.tvPlayerFour))))

    }


    @Test
    fun is_isButtonEndroundDisplayed() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.endround))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }



    @Test
    fun test_button1ClickableAndIncrement() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        onView(withId(R.id.button_player_1))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button2ClickableAndIncrement() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_2)).perform(ViewActions.click())
        onView(withId(R.id.button_player_2))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button3ClickableAndIncrement() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        onView(withId(R.id.button_player_3))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button4ClickableAndIncrement() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_4)).perform(ViewActions.click())
        onView(withId(R.id.button_player_4))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }


    @Test
    fun test_button1LongClickableAndIncrementFail() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_1)).perform(ViewActions.longClick())
        onView(withId(R.id.button_player_1))
            .check(ViewAssertions.matches(ViewMatchers.withText("LEFT")))
    }

    @Test
    fun test_button2LongClickableAndIncrementFail() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_2)).perform(ViewActions.longClick())
        onView(withId(R.id.button_player_2))
            .check(ViewAssertions.matches(ViewMatchers.withText("LEFT")))
    }

    @Test
    fun test_button3LongClickableAndIncrementFail() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_3)).perform(ViewActions.longClick())
        onView(withId(R.id.button_player_3))
            .check(ViewAssertions.matches(ViewMatchers.withText("LEFT")))
    }

    @Test
    fun test_button4LongClickableAndIncrementFail() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_4)).perform(ViewActions.longClick())
        onView(withId(R.id.button_player_4))
            .check(ViewAssertions.matches(ViewMatchers.withText("LEFT")))
    }




    @Test
    fun test_button1LongClickableAndIncrement() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        onView(withId(R.id.button_player_1)).perform(ViewActions.click())
        onView(withId(R.id.button_player_1)).perform(ViewActions.longClick())
        onView(withId(R.id.button_player_1))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button2LongClickableAndIncrement() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_2)).perform(ViewActions.click())
        onView(withId(R.id.button_player_2)).perform(ViewActions.click())
        onView(withId(R.id.button_player_2)).perform(ViewActions.longClick())
        onView(withId(R.id.button_player_2))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button3LongClickableAndIncrement() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        onView(withId(R.id.button_player_3)).perform(ViewActions.longClick())
        onView(withId(R.id.button_player_3))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun test_button4LongClickableAndIncrement() {
        onView(ViewMatchers.withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_4)).perform(ViewActions.click())
        onView(withId(R.id.button_player_4)).perform(ViewActions.click())
        onView(withId(R.id.button_player_4)).perform(ViewActions.longClick())
        onView(withId(R.id.button_player_4))
            .check(ViewAssertions.matches(ViewMatchers.withText("1")))
    }

    @Test
    fun is_tvPlayerRightScore() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.EndGameButton)).perform(ViewActions.click())

        onView(withId(R.id.button_player_1)).perform(ViewActions.longClick())
        onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        onView(withId(R.id.button_player_4)).perform(ViewActions.click())
        onView(withId(R.id.button_player_4)).perform(ViewActions.click())

        onView(withId(R.id.endround)).perform(ViewActions.click())




        assertEquals("23", getText(onView(withId(5))))
        assertEquals("26", getText(onView(withId(6))))
        assertEquals("20", getText(onView(withId(7))))
        assertEquals("19", getText(onView(withId(8))))
    }

    @Test
    fun readGameFromDatabase(){
        var scorePlayer1 = 0
        var scorePlayer2 = 0
        var scorePlayer3 = 0
        var scorePlayer4 = 0

        val scoreToSavePlayer1 = -1
        val scoreToSavePlayer2 = 0
        val scoreToSavePlayer3 = 1
        val scoreToSavePlayer4 = 2

        val gameObject = GameObject("Player 1","Player 2", "Player 3", "Player 4")
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        val newGameId = repo.createGame(gameObject,userID)
        val gameObjectFromDb = repo.getGame(newGameId)

        val new_round = RoundObject(scoreToSavePlayer1,scoreToSavePlayer2,scoreToSavePlayer3,scoreToSavePlayer4,0,0)
        repo.enterNewRound(new_round, newGameId)

        val cursor = repo.getCursor2(newGameId)
        if (cursor.moveToFirst()) {
            scorePlayer1 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER1_TICKS))
            scorePlayer2 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER2_TICKS))
            scorePlayer3 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER3_TICKS))
            scorePlayer4 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER4_TICKS))
        }

        assertEquals(scoreToSavePlayer1, scorePlayer1)
        assertEquals(scoreToSavePlayer2, scorePlayer2)
        assertEquals(scoreToSavePlayer3, scorePlayer3)
        assertEquals(scoreToSavePlayer4, scorePlayer4)

    }


    //helper function for comparing 2 strings from textboxes
    fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(TextView::class.java)
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