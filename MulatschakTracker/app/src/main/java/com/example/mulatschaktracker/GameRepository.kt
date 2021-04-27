package com.example.mulatschaktracker

import android.content.ContentValues
import android.content.Context

class GameRepository(var appContext: Context) {
    fun resetDatabase() {
        TODO("Not yet implemented")
    }

    fun createGame(newGameObject: GameObject): Long {

        val dbWrite = DataBaseHandler(appContext).writableDatabase
        val values = ContentValues()
        values.put(GAME_COLUMN_PLAYER1, newGameObject.player1)
        values.put(GAME_COLUMN_PLAYER2, newGameObject.player2)
        values.put(GAME_COLUMN_PLAYER3, newGameObject.player3)
        values.put(GAME_COLUMN_PLAYER4, newGameObject.player4)

        return dbWrite.insert(GAME_TABLE_NAME, null, values)

    }
    fun enterNewRound(valueList: ArrayList<Long>, gameID: Long): Long {
        TODO("Not yet implemented")
        return 0 //return game ID here
    }

}
