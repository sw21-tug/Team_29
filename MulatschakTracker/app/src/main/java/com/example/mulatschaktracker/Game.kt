package com.example.mulatschaktracker

import android.content.Intent
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_NUMBER
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mulatschaktracker.ui.addGameRound.AddGameRoundActivity

import com.example.mulatschaktracker.ui.home.GameRecyclerAdapter.GameViewHolder.Companion.GAME_ID
import kotlin.math.pow

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val repository = GameRepository(this)
        val game = repository.getGame(intent.getLongExtra(GAME_ID, 0))

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
        val game = repository.getGame(intent.getLongExtra(GAME_ID, 0))
        var score_p1: Int = 21
        var score_p2: Int = 21
        var score_p3: Int = 21
        var score_p4: Int = 21
        // text size in sp
        val textSize = 18F



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
            newText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            newText.gravity = Gravity.CENTER
            newRow.addView(newText, layoutParams)
        }
        tableLayout!!.addView(newRow)


        var cursor = repository.getCursorRounds(intent.getLongExtra(GAME_ID, 0))

        var rowIdCounter: Int = 12000
        var idcounter: Int = 4
        if (cursor.moveToFirst()) {
            do {
                val nrow = TableRow(this)
                val underDogCount = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_UNDERDOG))

                val tricksP1 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER1_TICKS))
                score_p1 = calcScore(score_p1, tricksP1,underDogCount)
                val newTextP1 = TextView(this)
                newTextP1.id = idcounter + 1
                newTextP1.inputType = TYPE_CLASS_NUMBER
                newTextP1.text = score_p1.toString()
                newTextP1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                newTextP1.gravity = Gravity.CENTER
                nrow.addView(newTextP1, layoutParams)
                idcounter = idcounter.plus(1)

                val tricksP2 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER2_TICKS))
                score_p2 = calcScore(score_p2, tricksP2, underDogCount)
                val newTextP2 = TextView(this)
                newTextP2.id = idcounter + 1
                newTextP2.inputType = TYPE_CLASS_NUMBER
                newTextP2.text = score_p2.toString()
                newTextP2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                newTextP2.gravity = Gravity.CENTER
                nrow.addView(newTextP2, layoutParams)
                idcounter = idcounter.plus(1)

                val tricksP3 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER3_TICKS))
                score_p3 = calcScore(score_p3, tricksP3,underDogCount)
                val newTextP3 = TextView(this)
                newTextP3.id = idcounter + 1
                newTextP3.inputType = TYPE_CLASS_NUMBER
                newTextP3.text = score_p3.toString()
                newTextP3.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                newTextP3.gravity = Gravity.CENTER
                nrow.addView(newTextP3, layoutParams)
                idcounter = idcounter.plus(1)

                val tricksP4 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER4_TICKS))
                score_p4 = calcScore(score_p4, tricksP4, underDogCount)
                val newTextP4 = TextView(this)
                newTextP4.id = idcounter + 1
                newTextP4.inputType = TYPE_CLASS_NUMBER
                newTextP4.text = score_p4.toString()
                newTextP4.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                newTextP4.gravity = Gravity.CENTER
                nrow.addView(newTextP4, layoutParams)
                idcounter = idcounter.plus(1)

                var rowId = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_ID))
                nrow.id = rowIdCounter

                rowIdCounter = rowIdCounter.plus(1)

                nrow.setOnLongClickListener{
                    editRound(rowId)
                    return@setOnLongClickListener true
                }



                tableLayout!!.addView(nrow)

            } while (cursor.moveToNext())
        }


    }


    fun addRound(view: View){
        var gameID = intent.getLongExtra(GAME_ID, 0)

        val bundle = Bundle()
        bundle.putLong(GAME_ID, gameID)
        val intent = Intent(this, AddGameRoundActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }

    fun editRound(ro: Int){
        var gameID = intent.getLongExtra(GAME_ID, 0)
        val bundle = Bundle()
        bundle.putLong(GAME_ID, gameID)
        bundle.putInt(ROUND_COLUMN_ID, ro)
        val intent = Intent(this, AddGameRoundActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }



    fun calcScore(current: Int, tricks: Int, UnderDog: Int) : Int
    {
        var deduction:Int
        var scoreMultiplicator =  2.0f
        scoreMultiplicator = scoreMultiplicator.pow(UnderDog)
        var scoreFactor = scoreMultiplicator.toInt()

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

        deduction = deduction * scoreFactor

        return (current + deduction)
    }
}