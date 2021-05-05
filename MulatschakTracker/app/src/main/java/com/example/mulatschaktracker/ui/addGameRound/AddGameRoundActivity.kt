package com.example.mulatschaktracker.ui.addGameRound

import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.TextView
import com.example.mulatschaktracker.GameRepository
import com.example.mulatschaktracker.R
import com.example.mulatschaktracker.ROUND_COLUMN_ID
import com.example.mulatschaktracker.RoundObject

class AddGameRoundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game_round)

        val bundle = intent.extras

        val roundId = if (bundle == null) 0 else bundle.getInt(ROUND_COLUMN_ID,0)
        val gameId = if (bundle == null) 0 else bundle.getLong(EXTRA_MESSAGE,0)

        val repository = GameRepository(this)
        val game = repository.getGame(gameId)




        var gameupdate = AddGameRound()

        val button_player1 = findViewById<Button>(R.id.button_player_1)
        val button_player2 = findViewById<Button>(R.id.button_player_2)
        val button_player3 = findViewById<Button>(R.id.button_player_3)
        val button_player4 = findViewById<Button>(R.id.button_player_4)

        val tv_playre1 = findViewById<TextView>(R.id.tvPlayerOne)
        val tv_playre2 = findViewById<TextView>(R.id.tvPlayerTwo)
        val tv_playre3 = findViewById<TextView>(R.id.tvPlayerThree)
        val tv_playre4 = findViewById<TextView>(R.id.tvPlayerFour)

        tv_playre1.text = game.player1
        tv_playre2.text = game.player2
        tv_playre3.text = game.player3
        tv_playre4.text = game.player4

        var ro: RoundObject? = null
        if(roundId > 0)
        {
            ro = repository.getRound(roundId)
            gameupdate.scorePlayer1 = ro.p1
            gameupdate.scorePlayer2 = ro.p2
            gameupdate.scorePlayer3 = ro.p3
            gameupdate.scorePlayer4 = ro.p4
        }


        button_player1.text = checkLeft(gameupdate.scorePlayer1)
        button_player2.text = checkLeft(gameupdate.scorePlayer2)
        button_player3.text = checkLeft(gameupdate.scorePlayer3)
        button_player4.text = checkLeft(gameupdate.scorePlayer4)

        val button_send_to_db = findViewById<Button>(R.id.endround)

        button_player1.setOnClickListener {
            gameupdate.incrementP1()
            button_player1.text = checkLeft(gameupdate.scorePlayer1)
        }

        button_player2.setOnClickListener {
            gameupdate.incrementP2()
            button_player2.text = checkLeft(gameupdate.scorePlayer2)
        }
        button_player3.setOnClickListener {
            gameupdate.incrementP3()
            button_player3.text = checkLeft(gameupdate.scorePlayer3)
        }
        button_player4.setOnClickListener {
            gameupdate.incrementP4()
            button_player4.text = checkLeft(gameupdate.scorePlayer4)
        }


        button_player1.setOnLongClickListener {
            if(gameupdate.scorePlayer1 > -1)
            {
                gameupdate.decrementP1()
                button_player1.text = checkLeft(gameupdate.scorePlayer1)
            }
            true

        }

        button_player2.setOnLongClickListener {
            if(gameupdate.scorePlayer2 > -1) {
                gameupdate.decrementP2()
                button_player2.text = checkLeft(gameupdate.scorePlayer2)
            }
            true
        }
        button_player3.setOnLongClickListener {
            if(gameupdate.scorePlayer3 > -1){
                gameupdate.decrementP3()
                button_player3.text = checkLeft(gameupdate.scorePlayer3)
            }
            true
        }
        button_player4.setOnLongClickListener {
            if(gameupdate.scorePlayer4 > -1){
                gameupdate.decrementP4()
                button_player4.text = checkLeft(gameupdate.scorePlayer4)
            }
            true
        }

        button_send_to_db.setOnClickListener {

            val new_round = RoundObject(gameupdate.scorePlayer1, gameupdate.scorePlayer2, gameupdate.scorePlayer3, gameupdate.scorePlayer4,0,0)
            if (roundId > 0  && ro != null)
            {
                ro.p1 = gameupdate.scorePlayer1
                ro.p2 = gameupdate.scorePlayer2
                ro.p3 = gameupdate.scorePlayer3
                ro.p4 = gameupdate.scorePlayer4
                repository.updateRound(roundId, ro)
            }
            else
            {
                repository.enterNewRound(new_round, gameId)
            }

            finish()
        }
    }


    private fun checkLeft(number:Int): String
    {
        if(number == -1)
        {
            return "Left"
        }
        else
        {
            return number.toString()
        }
    }
}
