package com.example.mulatschaktracker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

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

    fun enterNewRound(roundObject: RoundObject, gameID: Long): Long {
        TODO("Not yet implemented")
        return 0 //return game ID here
    }

    fun getGame(gameID: Long): GameObject {
        val cursor = getCursor(gameID)
        if (cursor.count == 1) {
            cursor.moveToFirst()
            val result = GameObject(cursor.getString(cursor.getColumnIndex(GAME_COLUMN_PLAYER1)),
                    cursor.getString(cursor.getColumnIndex(GAME_COLUMN_PLAYER2)),
                    cursor.getString(cursor.getColumnIndex(GAME_COLUMN_PLAYER3)),
                    cursor.getString(cursor.getColumnIndex(GAME_COLUMN_PLAYER4)))
            result.id = cursor.getLong(
                    cursor.getColumnIndex(GAME_COLUMN_ID))
            return result
        }
        throw Exception("game not found")
    }

    private fun getCursor(gameID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val projection =  arrayOf<String>(GAME_COLUMN_ID, GAME_COLUMN_PLAYER1, GAME_COLUMN_PLAYER2, GAME_COLUMN_PLAYER3, GAME_COLUMN_PLAYER4)
        val args = arrayOf<String>(gameID.toString())

        val query = "$GAME_COLUMN_ID like ?"
        return dbRead.query(GAME_TABLE_NAME, projection, query,args, null, null, null )
    }

}
