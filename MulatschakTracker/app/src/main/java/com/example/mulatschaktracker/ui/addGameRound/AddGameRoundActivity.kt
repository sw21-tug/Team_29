package com.example.mulatschaktracker.ui.addGameRound

import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.mulatschaktracker.GameRepository
import com.example.mulatschaktracker.R
import com.example.mulatschaktracker.ROUND_COLUMN_ID
import com.example.mulatschaktracker.RoundObject
import com.example.mulatschaktracker.ui.home.GameRecyclerAdapter.GameViewHolder.Companion.GAME_ID

class AddGameRoundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game_round)

        val bundle = intent.extras
        val roundId = if (bundle == null) 0 else bundle.getInt(ROUND_COLUMN_ID,0)
        val gameId = if (bundle == null) 0 else bundle.getLong(GAME_ID,0)
        if (gameId == 0L) {
            return
        }
        val repository = GameRepository(this)
        val game = repository.getGame(gameId)

        var gameupdate = AddGameRound()

        val buttonPlayer1 = findViewById<Button>(R.id.button_player_1)
        val buttonPlayer2 = findViewById<Button>(R.id.button_player_2)
        val buttonPlayer3 = findViewById<Button>(R.id.button_player_3)
        val buttonPlayer4 = findViewById<Button>(R.id.button_player_4)
        val buttonUnderDog = findViewById<Button>(R.id.UnderdogButton)

        val tv_playre1 = findViewById<TextView>(R.id.tvPlayerOne)
        val tv_playre2 = findViewById<TextView>(R.id.tvPlayerTwo)
        val tv_playre3 = findViewById<TextView>(R.id.tvPlayerThree)
        val tv_playre4 = findViewById<TextView>(R.id.tvPlayerFour)
        val tv_UnderDog = findViewById<TextView>(R.id.tvUnderdogRound)

        tv_playre1.text = game.player1
        tv_playre2.text = game.player2
        tv_playre3.text = game.player3
        tv_playre4.text = game.player4
        tv_UnderDog.text = gameupdate.Underdog.toString()


        var ro: RoundObject? = null
        if(roundId > 0)
        {
            ro = repository.getRound(roundId)
            gameupdate.scorePlayer1 = ro.p1
            gameupdate.scorePlayer2 = ro.p2
            gameupdate.scorePlayer3 = ro.p3
            gameupdate.scorePlayer4 = ro.p4
            gameupdate.Underdog = ro.ud
        }


        buttonPlayer1.text = checkLeft(gameupdate.scorePlayer1)
        buttonPlayer2.text = checkLeft(gameupdate.scorePlayer2)
        buttonPlayer3.text = checkLeft(gameupdate.scorePlayer3)
        buttonPlayer4.text = checkLeft(gameupdate.scorePlayer4)

        val buttonSendToDb = findViewById<Button>(R.id.endround)

        buttonPlayer1.setOnClickListener {
            gameupdate.incrementP1()
            buttonPlayer1.text = checkLeft(gameupdate.scorePlayer1)
        }

        buttonPlayer2.setOnClickListener {
            gameupdate.incrementP2()
            buttonPlayer2.text = checkLeft(gameupdate.scorePlayer2)
        }
        buttonPlayer3.setOnClickListener {
            gameupdate.incrementP3()
            buttonPlayer3.text = checkLeft(gameupdate.scorePlayer3)
        }
        buttonPlayer4.setOnClickListener {
            gameupdate.incrementP4()
            buttonPlayer4.text = checkLeft(gameupdate.scorePlayer4)
        }
        buttonUnderDog.setOnClickListener{
            gameupdate.incrementUnderdog()
            tv_UnderDog.text = gameupdate.Underdog.toString()
        }

        buttonUnderDog.setOnLongClickListener{
            if (gameupdate.Underdog >= 1) {
                gameupdate.decrementUnderdog()
            }
            tv_UnderDog.text = gameupdate.Underdog.toString()
            true
        }

        buttonPlayer1.setOnLongClickListener {
            if(gameupdate.scorePlayer1 > -1)
            {
                gameupdate.decrementP1()
                buttonPlayer1.text = checkLeft(gameupdate.scorePlayer1)
            }
            true
        }

        buttonPlayer2.setOnLongClickListener {
            if(gameupdate.scorePlayer2 > -1) {
                gameupdate.decrementP2()
                buttonPlayer2.text = checkLeft(gameupdate.scorePlayer2)
            }
            true
        }
        buttonPlayer3.setOnLongClickListener {
            if(gameupdate.scorePlayer3 > -1){
                gameupdate.decrementP3()
                buttonPlayer3.text = checkLeft(gameupdate.scorePlayer3)
            }
            true
        }
        buttonPlayer4.setOnLongClickListener {
            if(gameupdate.scorePlayer4 > -1){
                gameupdate.decrementP4()
                buttonPlayer4.text = checkLeft(gameupdate.scorePlayer4)
            }
            true
        }

        buttonSendToDb.setOnClickListener {

            val new_round = RoundObject(gameupdate.scorePlayer1, gameupdate.scorePlayer2, gameupdate.scorePlayer3, gameupdate.scorePlayer4,gameupdate.Underdog,0)
            if (roundId > 0  && ro != null)
            {
                ro.p1 = gameupdate.scorePlayer1
                ro.p2 = gameupdate.scorePlayer2
                ro.p3 = gameupdate.scorePlayer3
                ro.p4 = gameupdate.scorePlayer4
                ro.ud = gameupdate.Underdog
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
