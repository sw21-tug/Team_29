package com.example.mulatschaktracker.ui.addGameRound

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class AddGameRoundActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game_round)

        // Enables Always-on
        setAmbientEnabled()
    }
}