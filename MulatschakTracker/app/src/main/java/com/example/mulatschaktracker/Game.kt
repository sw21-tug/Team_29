package com.example.mulatschaktracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        var playerList = intent.getStringArrayListExtra("playerList")

        var textView = findViewById<TextView>(R.id.textViewPlayer1).apply {
            text = playerList?.elementAt(0)
        }
        textView = findViewById<TextView>(R.id.textViewPlayer2).apply {
            text = playerList?.elementAt(1)
        }
        textView = findViewById<TextView>(R.id.textViewPlayer3).apply {
            text = playerList?.elementAt(2)
        }
        textView = findViewById<TextView>(R.id.textViewPlayer4).apply {
            text = playerList?.elementAt(3)
        }
        val tableLayout = findViewById<TableLayout>(R.id.GameTable)
        val newRow = TableRow(this)
        val newText = TextView(this)

        var layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT)

        for (i in 0..3){
            val newText = TextView(this)
            newText.text = 21.toString()
            newText.gravity = Gravity.CENTER
            newRow.addView(newText, layoutParams)
        }
        tableLayout!!.addView(newRow)
    }
}