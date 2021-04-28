package com.example.mulatschaktracker.ui.addGameRound

class AddGameRound {
    private var scorePlayer1: Int? = null
    private var scorePlayer2: Int? = null
    private var scorePlayer3: Int? = null
    private var scorePlayer4: Int? = null

    init
    {
        this.scorePlayer1 = 0
        this.scorePlayer2 = 0
        this.scorePlayer3 = 0
        this.scorePlayer4 = 0

    }

    fun decrementP1()
    {
        this.scorePlayer1 = this.scorePlayer1?.minus(1)
    }

    fun decrementP2()
    {
        this.scorePlayer2 = this.scorePlayer2?.minus(1)
    }

    fun decrementP3()
    {
        this.scorePlayer3 = this.scorePlayer3?.minus(1)
    }

    fun decrementP4()
    {
        this.scorePlayer4 = this.scorePlayer4?.minus(1)
    }


    fun incrementP1()
    {
        this.scorePlayer1 = this.scorePlayer1?.plus(1)
    }

    fun incrementP2()
    {
        this.scorePlayer2 = this.scorePlayer2?.plus(1)
    }

    fun incrementP3()
    {
        this.scorePlayer3 = this.scorePlayer3?.plus(1)
    }

    fun incrementP4()
    {
        this.scorePlayer4 = this.scorePlayer4?.plus(1)
    }


    fun getScoreP1():Int?
    {
        return scorePlayer1
    }

    fun getScoreP2():Int?
    {
        return scorePlayer2
    }

    fun getScoreP3():Int?
    {
        return scorePlayer3
    }

    fun getScoreP4():Int?
    {
        return scorePlayer4
    }
}



