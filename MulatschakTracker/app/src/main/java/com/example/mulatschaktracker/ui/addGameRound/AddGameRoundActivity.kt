package com.example.mulatschaktracker.ui.addGameRound

import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.mulatschaktracker.GameRepository
import com.example.mulatschaktracker.R
import com.example.mulatschaktracker.RoundObject
import com.example.mulatschaktracker.ui.home.GameRecyclerAdapter.GameViewHolder.Companion.GAME_ID

class AddGameRoundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game_round)

        val repository = GameRepository(this)
        val game = repository.getGame(intent.getLongExtra(GAME_ID, 0))
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

        val button_send_to_db = findViewById<Button>(R.id.endround)

        button_player1.setOnClickListener {
            gameupdate.incrementP1()
            button_player1.text = checkLeft(gameupdate.getScoreP1())
        }

        button_player2.setOnClickListener {
            gameupdate.incrementP2()
            button_player2.text = checkLeft(gameupdate.getScoreP2())
        }
        button_player3.setOnClickListener {
            gameupdate.incrementP3()
            button_player3.text = checkLeft(gameupdate.getScoreP3())
        }
        button_player4.setOnClickListener {
            gameupdate.incrementP4()
            button_player4.text = checkLeft(gameupdate.getScoreP4())
        }


        button_player1.setOnLongClickListener {
            if(gameupdate.getScoreP1() > -1)
            {
                gameupdate.decrementP1()
                button_player1.text = checkLeft(gameupdate.getScoreP1())
            }
            true

        }

        button_player2.setOnLongClickListener {
            if(gameupdate.getScoreP2() > -1) {
                gameupdate.decrementP2()
                button_player2.text = checkLeft(gameupdate.getScoreP2())
            }
            true
        }
        button_player3.setOnLongClickListener {
            if(gameupdate.getScoreP3() > -1){
                gameupdate.decrementP3()
                button_player3.text = checkLeft(gameupdate.getScoreP3())
            }
            true
        }
        button_player4.setOnLongClickListener {
            if(gameupdate.getScoreP4() > -1){
                gameupdate.decrementP4()
                button_player4.text = checkLeft(gameupdate.getScoreP4())
            }
            true
        }

        button_send_to_db.setOnClickListener {

            val new_round = RoundObject(gameupdate.getScoreP1(),gameupdate.getScoreP2(),gameupdate.getScoreP3(),gameupdate.getScoreP4(),0,0)
            repository.enterNewRound(new_round, intent.getLongExtra(GAME_ID, 0))

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
