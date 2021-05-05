package com.example.mulatschaktracker

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.InputType.TYPE_CLASS_NUMBER
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mulatschaktracker.ui.addGameRound.AddGameRoundActivity


class Game : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val repository = GameRepository(this)
        val game = repository.getGame(intent.getLongExtra(EXTRA_MESSAGE, 0))

        findViewById<TextView>(R.id.textViewPlayer1).apply {
            text = game.player1
        }
        findViewById<TextView>(R.id.textViewPlayer2).apply {
            text = game.player2
        }
        findViewById<TextView>(R.id.textViewPlayer3).apply {
            text = game.player3
        }
        findViewById<TextView>(R.id.textViewPlayer4).apply {
            text = game.player4
        }

    }


    override fun onResume() {
        super.onResume()

        val repository = GameRepository(this)
        val game = repository.getGame(intent.getLongExtra(EXTRA_MESSAGE, 0))
        var score_p1: Int = 21
        var score_p2: Int = 21
        var score_p3: Int = 21
        var score_p4: Int = 21



        val tableLayout = findViewById<TableLayout>(R.id.GameTable)
        val childCount: Int = tableLayout.getChildCount()
        tableLayout.removeViews(1, childCount - 1)
        val newRow = TableRow(this)

        var layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        )

        for (i in 0..3){

            val newText = TextView(this)
            newText.id = i + 1
            newText.inputType = TYPE_CLASS_NUMBER
            newText.text = 21.toString()
            newText.gravity = Gravity.CENTER
            newRow.addView(newText, layoutParams)
        }
        tableLayout!!.addView(newRow)


        var cursor = repository.getCursor2(intent.getLongExtra(EXTRA_MESSAGE, 0))

        var idcounter: Int = 4
        if (cursor.moveToFirst()) {
            do {
                val nrow = TableRow(this)

                score_p1 = calcScore(score_p1, cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER1_TICKS)))
                val newTextP1 = TextView(this)
                newTextP1.id = idcounter + 1
                newTextP1.inputType = TYPE_CLASS_NUMBER
                newTextP1.text = score_p1.toString()
                newTextP1.gravity = Gravity.CENTER
                nrow.addView(newTextP1, layoutParams)
                idcounter = idcounter.plus(1)

                score_p2 = calcScore(score_p2, cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER2_TICKS)))
                val newTextP2 = TextView(this)
                newTextP2.id = idcounter + 1
                newTextP2.inputType = TYPE_CLASS_NUMBER
                newTextP2.text = score_p2.toString()
                newTextP2.gravity = Gravity.CENTER
                nrow.addView(newTextP2, layoutParams)
                idcounter = idcounter.plus(1)

                score_p3 = calcScore(score_p3, cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER3_TICKS)))
                val newTextP3 = TextView(this)
                newTextP3.id = idcounter + 1
                newTextP3.inputType = TYPE_CLASS_NUMBER
                newTextP3.text = score_p3.toString()
                newTextP3.gravity = Gravity.CENTER
                nrow.addView(newTextP3, layoutParams)
                idcounter = idcounter.plus(1)

                score_p4 = calcScore(score_p4, cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER4_TICKS)))
                val newTextP4 = TextView(this)
                newTextP4.id = idcounter + 1
                newTextP4.inputType = TYPE_CLASS_NUMBER
                newTextP4.text = score_p4.toString()
                newTextP4.gravity = Gravity.CENTER
                nrow.addView(newTextP4, layoutParams)
                idcounter = idcounter.plus(1)


                tableLayout!!.addView(nrow)

            } while (cursor.moveToNext())
        }


    }


    fun addRound(view: View){
        var gameID = intent.getLongExtra(EXTRA_MESSAGE, 0)
        val intent = Intent(this, AddGameRoundActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, gameID)
        }
        startActivity(intent)
    }



    fun calcScore(current: Int, tricks: Int) : Int
    {
        var deduction:Int
        if(tricks == -1)
        {
            deduction = 2
        }else if (tricks == 0)
        {
            deduction = 5
        }
        else
        {
            deduction = tricks * -1
        }

        return current + deduction
    }
}