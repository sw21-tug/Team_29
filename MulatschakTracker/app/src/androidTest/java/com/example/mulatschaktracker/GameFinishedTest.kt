package com.example.mulatschaktracker


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

// SOME testCases die because the ui craches

@RunWith(AndroidJUnit4::class)
class GameFinishedTest : TestCase(){
    lateinit var scenario : ActivityScenario<MainActivity>
    val username: String = "Player 1"

    private val waitTime:Long = 400

    @Before
    public override fun setUp(){

        super.setUp()
        val  appContext: Context = ApplicationProvider.getApplicationContext()
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

    @Test
    fun user1With0Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(click())

        }
        onView(withId(R.id.endround)).perform(click())


        onView(allOf(withId(R.id.Game_Finished),
            withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun noOneWon() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        onView(withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        onView(withId(R.id.AddRoundButton)).perform(ViewActions.click())
        onView(withId(R.id.button_player_3)).perform(ViewActions.click())
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(ViewActions.click())
        onView(withId(R.id.Game_Finished)).check(doesNotExist())
    }
    @Test
    fun user3With100Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..14) {
            Thread.sleep(waitTime)
            onView(withId(R.id.button_player_1)).perform(click())
            onView(withId(R.id.endround)).perform(click())
            onView(withId(R.id.AddRoundButton)).perform(click())

        }
        onView(withId(R.id.button_player_1)).perform(click())

        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }


    @Test
    fun noOneWon() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())
        onView(withId(R.id.button_player_3)).perform(click())
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())
        onView(withId(R.id.Game_Finished)).check(doesNotExist())
    }


    @Test
    fun users2With100Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..14) {
            Thread.sleep(waitTime)
            onView(withId(R.id.button_player_2)).perform(click())
            onView(withId(R.id.button_player_3)).perform(click())

            onView(withId(R.id.endround)).perform(click())
            Thread.sleep(waitTime)
            onView(withId(R.id.AddRoundButton)).perform(click())

        }
        onView(withId(R.id.button_player_2)).perform(click())
        onView(withId(R.id.button_player_3)).perform(click())
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }

    @Test
    fun users1With100Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..14) {

            Thread.sleep(waitTime)
            onView(withId(R.id.button_player_1)).perform(click())
            onView(withId(R.id.button_player_2)).perform(click())
            onView(withId(R.id.button_player_3)).perform(click())

            onView(withId(R.id.endround)).perform(click())
            Thread.sleep(waitTime)
            onView(withId(R.id.AddRoundButton)).perform(click())


        }
        onView(withId(R.id.button_player_1)).perform(click())
        onView(withId(R.id.button_player_2)).perform(click())
        onView(withId(R.id.button_player_3)).perform(click())
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }


    @Test
    fun user2With0Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_2)).perform(click())
            onView(withId(R.id.button_player_3)).perform(click())

        }
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }


    @Test
    fun user3With0Points() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(click())
            onView(withId(R.id.button_player_2)).perform(click())
            onView(withId(R.id.button_player_3)).perform(click())
        }
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
    }

    @Test
    fun oneWinner() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(click())

        }
        onView(withId(R.id.endround)).perform(click())

        onView(allOf(withId(R.id.Game_Finished),
            withEffectiveVisibility(Visibility.VISIBLE)))

        onView(allOf(withId(R.id.textView),
            withEffectiveVisibility(Visibility.VISIBLE)))


        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 ")))
    }

    @Test
    fun twoWinners() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(click())
            onView(withId(R.id.button_player_1)).perform(click())
        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 Player 3 ")))
       // assert(false)

    }

    @Test
    fun threeWinners() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(click())
            onView(withId(R.id.button_player_1)).perform(click())
            onView(withId(R.id.button_player_2)).perform(click())

        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 Player 2 Player 3 ")))

    }

    @Test
    fun nameforthewinnders() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(click())
            onView(withId(R.id.button_player_1)).perform(click())
            onView(withId(R.id.button_player_2)).perform(click())
            onView(withId(R.id.button_player_4)).perform(click())


        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 Player 2 Player 3 Player 4 ")))

    }

    @Test
    fun normalgame() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(click())


        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 ")))
        onView(withId(R.id.textView2)).check(matches(withText("2. Place Player 2")))
        onView(withId(R.id.textView3)).check(matches(withText("3. Place Player 3")))
        onView(withId(R.id.textView4)).check(matches(withText("4. Place Player 4")))


    }

    @Test
    fun p1324() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(click())

        }
        onView(withId(R.id.button_player_3)).perform(click())
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 ")))
        onView(withId(R.id.textView2)).check(matches(withText("2. Place Player 3")))
        onView(withId(R.id.textView3)).check(matches(withText("3. Place Player 2")))
        onView(withId(R.id.textView4)).check(matches(withText("4. Place Player 4")))
    }

    @Test
    fun name3100() {
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..14) {
            Thread.sleep(waitTime)
            onView(withId(R.id.button_player_1)).perform(click())
            onView(withId(R.id.button_player_2)).perform(click())
            onView(withId(R.id.button_player_3)).perform(click())

            onView(withId(R.id.endround)).perform(click())
            Thread.sleep(waitTime)
            onView(withId(R.id.AddRoundButton)).perform(click())

        }
        onView(withId(R.id.endround)).perform(click())
        onView(withId(R.id.Game_Finished)).check(matches(isDisplayed()))
        onView(withId(R.id.textView)).check(matches(withText("1. Place Player 1 Player 2 Player 3 ")))
        onView(withId(R.id.textView2)).check(matches(withText("2. Place Player 4")))
    }

    @Test
    fun gameFinishedDb()
    {
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        //repo.resetDatabase()
        // maybe reset the database
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

        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(click())

        }
        onView(withId(R.id.endround)).perform(click())

        val gameobject =  repo.getGame(1)
        assert(gameobject.player1 ==  "Test Player 1")
       val game =  repo.getGameFinished(1)
        assert(game == 1)



    }

    @Test
    fun getWinnerList3()
    {
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)

        onView(withId(R.id.StartNewGameActivityButton)).perform(click())


        onView(withId(R.id.StartNewGameButton)).perform(click())

        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_3)).perform(click())

        }
        onView(withId(R.id.endround)).perform(click())

        val game =  repo.getGameFinished(1)
        assert(game == 1)
        val listOfWinners = repo.getWinners(1)
        assert(listOfWinners.player1 == "Player 3")
    }



    @Test
    fun getWinnerList4()
    {
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)

        onView(withId(R.id.StartNewGameActivityButton)).perform(click())


        onView(withId(R.id.StartNewGameButton)).perform(click())

        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_4)).perform(click())

        }
        onView(withId(R.id.endround)).perform(click())

        val game =  repo.getGameFinished(1)
        assert(game == 1)
        val listOfWinners = repo.getWinners(1)
        assert(listOfWinners.player1 == "Player 4")
    }

    @Test
    fun getWinnerList1()
    {

        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)

        onView(withId(R.id.StartNewGameActivityButton)).perform(click())


        onView(withId(R.id.StartNewGameButton)).perform(click())

        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(click())
          //  onView(withId(R.id.button_player_2)).perform(click())
        }
        onView(withId(R.id.endround)).perform(click())

        val game =  repo.getGameFinished(1)
        assert(game == 1)
        val listOfWinners = repo.getWinners(1)
        assert(listOfWinners.player1 == "Player 1")

    }


    @Test
    fun getWinnerList12()
    {

        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())


        onView(withId(R.id.StartNewGameButton)).perform(click())

        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(click())
            onView(withId(R.id.button_player_2)).perform(click())
        }
        onView(withId(R.id.endround)).perform(click())

        val game =  repo.getGameFinished(1)
        assert(game == 1)
        val listOfWinners = repo.getWinners(1)
        assert(listOfWinners.player1 == "Player 1")
        assert(listOfWinners.player2 == "Player 2")

    }



    @Test
    fun getWinnerList123()
    {

        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)

        onView(withId(R.id.StartNewGameActivityButton)).perform(click())


        onView(withId(R.id.StartNewGameButton)).perform(click())

        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(click())
            onView(withId(R.id.button_player_2)).perform(click())
            onView(withId(R.id.button_player_3)).perform(click())

        }
        onView(withId(R.id.endround)).perform(click())

        val game =  repo.getGameFinished(1)
        assert(game == 1)
        val listOfWinners = repo.getWinners(1)
        assert(listOfWinners.player1 == "Player 1")
        assert(listOfWinners.player2 == "Player 2")
        assert(listOfWinners.player3 == "Player 3")
    }


    @Test
    fun getWinnerList1234()
    {
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())


        onView(withId(R.id.StartNewGameButton)).perform(click())

        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(click())
            onView(withId(R.id.button_player_2)).perform(click())
            onView(withId(R.id.button_player_3)).perform(click())
            onView(withId(R.id.button_player_4)).perform(click())
        }
        onView(withId(R.id.endround)).perform(click())

        val game =  repo.getGameFinished(1)
        assert(game == 1)
        val listOfWinners = repo.getWinners(1)
        assert(listOfWinners.player1 == "Player 1")
        assert(listOfWinners.player2 == "Player 2")
        assert(listOfWinners.player3 == "Player 3")
        assert(listOfWinners.player4 == "Player 4")
    }



    @Test
    fun getWinnerList13()
    {

        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())


        onView(withId(R.id.StartNewGameButton)).perform(click())

        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(click())
            onView(withId(R.id.button_player_3)).perform(click())
        }
        onView(withId(R.id.endround)).perform(click())

        val game =  repo.getGameFinished(1)
        assert(game == 1)
        val listOfWinners = repo.getWinners(1)
        assert(listOfWinners.player1 == "Player 1")
        assert(listOfWinners.player2 == "Player 3")
    }





    @Test
    fun getWinnerList24()
    {
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)
        onView(withId(R.id.StartNewGameActivityButton)).perform(click())


        onView(withId(R.id.StartNewGameButton)).perform(click())

        onView(withId(R.id.AddRoundButton)).perform(click())

        for (i in 0..21) {
            onView(withId(R.id.button_player_2)).perform(click())
            onView(withId(R.id.button_player_4)).perform(click())
        }
        onView(withId(R.id.endround)).perform(click())

        val game =  repo.getGameFinished(1)
        assert(game == 1)
        val listOfWinners = repo.getWinners(1)
        assert(listOfWinners.player1 == "Player 2")
        assert(listOfWinners.player2 == "Player 4")
    }

    @Test
    fun backButton()
    {


        val appContext: Context = ApplicationProvider.getApplicationContext()
        val repo = GameRepository(appContext)

        onView(withId(R.id.StartNewGameActivityButton)).perform(click())
        onView(withId(R.id.StartNewGameButton)).perform(click())
        onView(withId(R.id.AddRoundButton)).perform(ViewActions.click())


        for (i in 0..21) {
            onView(withId(R.id.button_player_1)).perform(click())
            onView(withId(R.id.button_player_2)).perform(click())
        }
        onView(withId(R.id.endround)).perform(click())

        onView(withId(R.id.game_finished_back_button)).perform(click())

        onView(withId(R.id.navigation_home)).check(matches(isDisplayed()))

    }

}
