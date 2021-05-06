package com.example.mulatschaktracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.mulatschaktracker.ui.home.GameRecyclerAdapter.GameViewHolder.Companion.GAME_ID

class StartNewGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.title_startnewgame)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_new_game)
    }

    fun startGame(view : View) {
        val gameRepository = GameRepository(this)
        val userRepository = UserRepository(this)
        val editTextPlayer1 = findViewById<EditText>(R.id.Player1_EditText)
        val editTextPlayer2 = findViewById<EditText>(R.id.Player2_EditText)
        val editTextPlayer3 = findViewById<EditText>(R.id.Player3_EditText)
        val editTextPlayer4 = findViewById<EditText>(R.id.Player4_EditText)

        val namePlayer1 = if (editTextPlayer1.text.toString() != "") editTextPlayer1.text.toString() else editTextPlayer1.hint.toString()
        val namePlayer2 = if (editTextPlayer2.text.toString() != "") editTextPlayer2.text.toString() else editTextPlayer2.hint.toString()
        val namePlayer3 = if (editTextPlayer3.text.toString() != "") editTextPlayer3.text.toString() else editTextPlayer3.hint.toString()
        val namePlayer4 = if (editTextPlayer4.text.toString() != "") editTextPlayer4.text.toString() else editTextPlayer4.hint.toString()

        //creating Game Object
        val newGameObject = GameObject(namePlayer1, namePlayer2, namePlayer3, namePlayer4)

        val preferences = getSharedPreferences(PREFERENCENAME, MODE_PRIVATE)
        val userName = preferences.getString(LASTUSER, "")
        val user = userName?.let { userRepository.getUser(it) }

        val gameID = user?.let { gameRepository.createGame(newGameObject, it.id) }

        val intent = Intent(this, Game::class.java).apply {
            putExtra(GAME_ID, gameID)
        }

        startActivity(intent)

    }
}