package com.example.mulatschaktracker.ui.addGameRound

import android.widget.TextView
import com.example.mulatschaktracker.GameRepository
import com.example.mulatschaktracker.R

class AddGameRound {
    var scorePlayer1: Int
    var scorePlayer2: Int
    var scorePlayer3: Int
    var scorePlayer4: Int
    var Underdog: Int = 0
    var Heartround: Boolean = false


    init
    {

        this.scorePlayer1 = 0
        this.scorePlayer2 = 0
        this.scorePlayer3 = 0
        this.scorePlayer4 = 0
        this.Underdog = 0


    }

    fun decrementP1()
    {
        this.scorePlayer1 = this.scorePlayer1.minus(1)
    }

    fun decrementP2()
    {
        this.scorePlayer2 = this.scorePlayer2.minus(1)
    }

    fun decrementP3()
    {
        this.scorePlayer3 = this.scorePlayer3.minus(1)
    }

    fun decrementP4()
    {
        this.scorePlayer4 = this.scorePlayer4.minus(1)
    }


    fun incrementP1()
    {
        this.scorePlayer1 = this.scorePlayer1.plus(1)
    }

    fun incrementP2()
    {
        this.scorePlayer2 = this.scorePlayer2.plus(1)
    }

    fun incrementP3()
    {
        this.scorePlayer3 = this.scorePlayer3.plus(1)
    }

    fun incrementP4()
    {
        this.scorePlayer4 = this.scorePlayer4.plus(1)
    }

    fun incrementUnderdog()
    {
        this.Underdog = this.Underdog.plus(1)
    }

    fun decrementUnderdog()
    {
        this.Underdog = this.Underdog.minus(1)
    }

    fun setHeartRound(active: Boolean)
    {
        this.Heartround = active;
    }


}



