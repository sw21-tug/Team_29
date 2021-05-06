package com.example.mulatschaktracker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class GameRepository(var appContext: Context) {

    fun createGame(newGameObject: GameObject, userID: Long): Long {

        val dbWrite = DataBaseHandler(appContext).writableDatabase
        val values = ContentValues()
        values.put(GAME_COLUMN_PLAYER1, newGameObject.player1)
        values.put(GAME_COLUMN_PLAYER2, newGameObject.player2)
        values.put(GAME_COLUMN_PLAYER3, newGameObject.player3)
        values.put(GAME_COLUMN_PLAYER4, newGameObject.player4)
        values.put(GAME_COLUMN_USER_ID, userID)

        return dbWrite.insert(GAME_TABLE_NAME, null, values)
    }

    fun enterNewRound(roundObject: RoundObject, gameID: Long): Long {

        val dbWrite = DataBaseHandler(appContext).writableDatabase
        val values = ContentValues()
        values.put(ROUND_COLUMN_GAME_ID, gameID.toInt())
        values.put(ROUND_COLUMN_PLAYER1_TICKS, roundObject.p1)
        values.put(ROUND_COLUMN_PLAYER2_TICKS, roundObject.p2)
        values.put(ROUND_COLUMN_PLAYER3_TICKS, roundObject.p3)
        values.put(ROUND_COLUMN_PLAYER4_TICKS, roundObject.p4)
        values.put(ROUND_COLUMN_UNDERDOG, roundObject.ud)
        values.put(ROUND_COLUMN_HEARTROUND, roundObject.hr)

        return dbWrite.insert(ROUND_TABLE_NAME, null, values)
    }

    /**
     *  Drops and recreates the whole database
     *  Use with CAUTION!!!
     */
    fun resetDatabase() {
        val db =  DataBaseHandler(appContext).writableDatabase
        DataBaseHandler(appContext).onUpgrade(db, 0, 0)
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

    fun getGames(userID: Long): List<GameObject> {
        val cursor = getCursorByUser(userID)
        var gameList : MutableList<GameObject> = ArrayList()
        if(cursor.count > 0){
            cursor.moveToFirst()
            do {
                val game = GameObject(cursor.getString(cursor.getColumnIndex(GAME_COLUMN_PLAYER1)),
                        cursor.getString(cursor.getColumnIndex(GAME_COLUMN_PLAYER2)),
                        cursor.getString(cursor.getColumnIndex(GAME_COLUMN_PLAYER3)),
                        cursor.getString(cursor.getColumnIndex(GAME_COLUMN_PLAYER4)))
                game.id = cursor.getLong(
                        cursor.getColumnIndex(GAME_COLUMN_ID))
                gameList.add(game)
            } while (cursor.moveToNext())

            return gameList
        }
        return gameList
    }

    private fun getCursorByUser(userID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val projection =  arrayOf<String>(GAME_COLUMN_ID, GAME_COLUMN_PLAYER1, GAME_COLUMN_PLAYER2, GAME_COLUMN_PLAYER3, GAME_COLUMN_PLAYER4)
        val args = arrayOf<String>(userID.toString())

        val query = "$GAME_COLUMN_USER_ID like ?"
        return dbRead.query(GAME_TABLE_NAME, projection, query,args, null, null, null )
    }

    private fun getCursor(gameID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val projection =  arrayOf<String>(GAME_COLUMN_ID, GAME_COLUMN_PLAYER1, GAME_COLUMN_PLAYER2, GAME_COLUMN_PLAYER3, GAME_COLUMN_PLAYER4)
        val args = arrayOf<String>(gameID.toString())

        val query = "$GAME_COLUMN_ID like ?"
        return dbRead.query(GAME_TABLE_NAME, projection, query, args, null, null, null )
    }

    fun getCursor2(gameID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val projection =  arrayOf<String>(ROUND_COLUMN_ID, ROUND_COLUMN_PLAYER1_TICKS, ROUND_COLUMN_PLAYER2_TICKS, ROUND_COLUMN_PLAYER3_TICKS, ROUND_COLUMN_PLAYER4_TICKS)
        val args = arrayOf<String>(gameID.toString())

        val query = "$ROUND_COLUMN_GAME_ID like ?"
        return dbRead.query(ROUND_TABLE_NAME, projection, query, args, null, null, null )
    }

    fun getCursorRounds(gameID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val projection =  arrayOf<String>(ROUND_COLUMN_ID, ROUND_COLUMN_PLAYER1_TICKS, ROUND_COLUMN_PLAYER2_TICKS, ROUND_COLUMN_PLAYER3_TICKS, ROUND_COLUMN_PLAYER4_TICKS)
        val args = arrayOf<String>(gameID.toString())

        val query = "SELECT * FROM " + ROUND_TABLE_NAME + " WHERE " + ROUND_COLUMN_GAME_ID + " = " + gameID
        return dbRead.rawQuery(query, null )
    }

    //fun getRounds(gameID: Long):

}
