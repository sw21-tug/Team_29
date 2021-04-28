package com.example.mulatschaktracker.ui.addGameRound

import org.junit.Assert.*
import com.example.mulatschaktracker.ui.addGameRound.AddGameRound
import org.junit.Assert
import org.junit.Test

class AddGameRoundTest
{

    var gameupdate = AddGameRound();
    @Test
    fun incrementInitialP1() {
        Assert.assertEquals(0, gameupdate.getScoreP1())
    }

    @Test
    fun incrementInitialP2() {
        Assert.assertEquals(0, gameupdate.getScoreP2())
    }

    @Test
    fun incrementInitialP3() {
        Assert.assertEquals(0, gameupdate.getScoreP3())
    }

    @Test
    fun incrementInitialP4() {
        Assert.assertEquals(0, gameupdate.getScoreP4())
    }


    @Test
    fun incrementP1() {

        gameupdate.incrementP1()
        Assert.assertEquals(1, gameupdate.getScoreP1())
    }

    @Test
    fun incrementP2() {

        gameupdate.incrementP2()
        Assert.assertEquals(1, gameupdate.getScoreP2())
    }

    @Test
    fun incrementP3() {

        gameupdate.incrementP3()
        Assert.assertEquals(1, gameupdate.getScoreP3())
    }


    @Test
    fun incrementP4() {

        gameupdate.incrementP4()
        Assert.assertEquals(1, gameupdate.getScoreP4())
    }

}