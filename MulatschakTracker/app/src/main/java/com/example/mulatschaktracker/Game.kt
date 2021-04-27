package com.example.mulatschaktracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.InputType.TYPE_CLASS_NUMBER
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

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

        val tableLayout = findViewById<TableLayout>(R.id.GameTable)
        val newRow = TableRow(this)

        var layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT)

        for (i in 0..3){
            val newText = TextView(this)
            newText.id = i + 1
            newText.inputType = TYPE_CLASS_NUMBER
            newText.text = 21.toString()
            newText.gravity = Gravity.CENTER
            newRow.addView(newText, layoutParams)
        }
        tableLayout!!.addView(newRow)
    }
}