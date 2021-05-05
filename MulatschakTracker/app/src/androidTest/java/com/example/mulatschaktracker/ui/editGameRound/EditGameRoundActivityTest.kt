package com.example.mulatschaktracker.ui.editGameRound

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.mulatschaktracker.MainActivity
import com.example.mulatschaktracker.R
import com.example.mulatschaktracker.ui.addGameRound.AddGameRoundActivityTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class EditGameRoundActivityTest {



    @get: Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    private val waitTime:Long = 400


    /**
     * Setup test data
     */
    @Before
    fun setupTests(){
        Espresso.onView(ViewMatchers.withId(R.id.StartNewGameActivityButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.StartNewGameButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.AddRoundButton)).perform(ViewActions.click())
        Thread.sleep(waitTime)
        Espresso.onView(ViewMatchers.withId(R.id.button_player_1)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_1)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_2)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_4)).perform(ViewActions.longClick())

        closeAndOpenNewRound()

        Espresso.onView(ViewMatchers.withId(R.id.button_player_2)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_2)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_3)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_4)).perform(ViewActions.longClick())

        closeAndOpenNewRound()

        Espresso.onView(ViewMatchers.withId(R.id.button_player_1)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_2)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_4)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_player_4)).perform(ViewActions.click())
        Thread.sleep(waitTime)
        Espresso.onView(ViewMatchers.withId(R.id.endround)).perform(ViewActions.click())
    }

    private fun closeAndOpenNewRound() {
        Thread.sleep(waitTime)
        Espresso.onView(ViewMatchers.withId(R.id.endround)).perform(ViewActions.click())
        Thread.sleep(waitTime)
        Espresso.onView(ViewMatchers.withId(R.id.AddRoundButton)).perform(ViewActions.click())
        Thread.sleep(waitTime)
    }

    /**
     * check all rows at once since setup of test data is expensive
     */
    @Test
    fun performLongClickOnRow() {
        Espresso.onView(ViewMatchers.withId(12000)).perform(ViewActions.longClick())
        checkEditScreen("2", "1", "0", "LEFT")
        Espresso.onView(ViewMatchers.withId(R.id.endround)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(12001)).perform(ViewActions.longClick())
        checkEditScreen("0", "2", "1", "LEFT")
        Espresso.onView(ViewMatchers.withId(R.id.endround)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(12002)).perform(ViewActions.longClick())
        checkEditScreen("1", "1", "0", "2")
    }

    @Test
    fun performEditRow1addOne() {
        val rowId = 12000
        testRow(rowId, R.id.button_player_1, 5, "18",true)
        testRow(rowId, R.id.button_player_2, 6, "19",true)
        testRow(rowId, R.id.button_player_3, 7, "20",true)
        testRow(rowId, R.id.button_player_4, 8, "26",true)
    }

    @Test
    fun performEditRow1subtractOne() {
        val rowId = 12000
        testRow(rowId, R.id.button_player_1, 5, "20",false)
        testRow(rowId, R.id.button_player_2, 6, "26",false)
        testRow(rowId, R.id.button_player_3, 7, "23",false)
        testRow(rowId, R.id.button_player_4, 8, "23",false)
    }

    @Test
    fun performEditRow2addOne() {
        val rowId = 12001
        testRow(rowId, R.id.button_player_1, 9, "18",true)
        testRow(rowId, R.id.button_player_2, 10, "17",true)
        testRow(rowId, R.id.button_player_3, 11, "24",true)
        testRow(rowId, R.id.button_player_4, 12, "28",true)
    }

    @Test
    fun performEditRow2subtractOne() {
        val rowId = 12001
        testRow(rowId, R.id.button_player_1, 9, "21",false)
        testRow(rowId, R.id.button_player_2, 10, "19",false)
        testRow(rowId, R.id.button_player_3, 11, "31",false)
        testRow(rowId, R.id.button_player_4, 12, "25",false)
    }

    @Test
    fun valuePropagationRow1AddTest() {
        val rowId = 12000
        checkValuePropagationAdd(rowId, R.id.button_player_1, arrayOf(1,5,9,13), arrayOf("21", "18", "23", "22"), true)
        checkValuePropagationAdd(rowId, R.id.button_player_2, arrayOf(2,6,10,14), arrayOf("21", "19", "17", "16"), true)
        checkValuePropagationAdd(rowId, R.id.button_player_3, arrayOf(3,7,11,15), arrayOf("21", "20", "19", "24"), true)
        checkValuePropagationAdd(rowId, R.id.button_player_4, arrayOf(4,8,12,16), arrayOf("21", "26", "28", "26"),true)
    }

    @Test
    fun valuePropagationRow1SubstractTest() {
        val rowId = 12000
        checkValuePropagationAdd(rowId, R.id.button_player_1, arrayOf(1,5,9,13), arrayOf("21", "20", "25", "24"),false)
        checkValuePropagationAdd(rowId, R.id.button_player_2, arrayOf(2,6,10,14), arrayOf("21", "26", "24", "23"),false)
        checkValuePropagationAdd(rowId, R.id.button_player_3, arrayOf(3,7,11,15), arrayOf("21", "23", "22", "27"),false)
        checkValuePropagationAdd(rowId, R.id.button_player_4, arrayOf(4,8,12,16), arrayOf("21", "23", "25", "23"),false)
    }

    @Test
    fun valuePropagationRow2AddTest() {
        val rowId = 12001
        checkValuePropagationAdd(rowId, R.id.button_player_1, arrayOf(1,5,9,13), arrayOf("21", "19", "18", "17"), true)
        checkValuePropagationAdd(rowId, R.id.button_player_2, arrayOf(2,6,10,14), arrayOf("21", "20", "17", "16"), true)
        checkValuePropagationAdd(rowId, R.id.button_player_3, arrayOf(3,7,11,15), arrayOf("21", "26", "24", "29"), true)
        checkValuePropagationAdd(rowId, R.id.button_player_4, arrayOf(4,8,12,16), arrayOf("21", "23", "28", "26"),true)
    }

    @Test
    fun valuePropagationRow2SubstractTest() {
        val rowId = 12001
        checkValuePropagationAdd(rowId, R.id.button_player_1, arrayOf(1,5,9,13), arrayOf("21", "19", "21", "20"),false)
        checkValuePropagationAdd(rowId, R.id.button_player_2, arrayOf(2,6,10,14), arrayOf("21", "20", "19", "18"),false)
        checkValuePropagationAdd(rowId, R.id.button_player_3, arrayOf(3,7,11,15), arrayOf("21", "26", "31", "36"),false)
        checkValuePropagationAdd(rowId, R.id.button_player_4, arrayOf(4,8,12,16), arrayOf("21", "23", "25", "23"),false)
    }

    private fun checkValuePropagationAdd(rowId:Int, buttonId:Int, labelIds:Array<Int>, results:Array<String>, add :
    Boolean) {
        Espresso.onView(ViewMatchers.withId(rowId)).perform(ViewActions.longClick())
        Thread.sleep(waitTime)
        if (add) {
            Espresso.onView(ViewMatchers.withId(buttonId)).perform(ViewActions.click())
        } else {
            Espresso.onView(ViewMatchers.withId(buttonId)).perform(ViewActions.longClick())
        }
        Thread.sleep(waitTime)
        Espresso.onView(ViewMatchers.withId(R.id.endround)).perform(ViewActions.click())
        Thread.sleep(waitTime)
        for (i in 0..(labelIds.size-1)) {
            Assert.assertEquals(results[i], AddGameRoundActivityTest.getText(Espresso.onView(ViewMatchers.withId
                (labelIds[i]))))
        }
        Thread.sleep(waitTime)
    }

    private fun testRow(rowId: Int, buttonId: Int, labelId: Int, result: String, add:Boolean) {
        Espresso.onView(ViewMatchers.withId(rowId)).perform(ViewActions.longClick())
        Thread.sleep(waitTime)
        if (add) {
            Espresso.onView(ViewMatchers.withId(buttonId)).perform(ViewActions.click())
        } else {
            Espresso.onView(ViewMatchers.withId(buttonId)).perform(ViewActions.longClick())
        }
        Thread.sleep(waitTime)
        Espresso.onView(ViewMatchers.withId(R.id.endround)).perform(ViewActions.click())
        Thread.sleep(waitTime)
        Assert.assertEquals(result, AddGameRoundActivityTest.getText(Espresso.onView(ViewMatchers.withId(labelId))))
    }

    private fun checkEditScreen(b1:String, b2:String, b3:String, b4:String) {
        Thread.sleep(waitTime)
        Espresso.onView(ViewMatchers.withId(R.id.button_player_1))
            .check(ViewAssertions.matches(ViewMatchers.withText(b1)))
        Espresso.onView(ViewMatchers.withId(R.id.button_player_2))
            .check(ViewAssertions.matches(ViewMatchers.withText(b2)))
        Espresso.onView(ViewMatchers.withId(R.id.button_player_3))
            .check(ViewAssertions.matches(ViewMatchers.withText(b3)))
        Espresso.onView(ViewMatchers.withId(R.id.button_player_4))
            .check(ViewAssertions.matches(ViewMatchers.withText(b4)))
    }

}

