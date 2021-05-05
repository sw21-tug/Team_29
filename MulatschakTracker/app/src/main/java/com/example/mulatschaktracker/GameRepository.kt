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
        return dbRead.query(GAME_TABLE_NAME, projection, query, args, null, null, null )
    }

    fun getCursorRounds(gameID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val projection =  arrayOf<String>(ROUND_COLUMN_ID, ROUND_COLUMN_PLAYER1_TICKS, ROUND_COLUMN_PLAYER2_TICKS, ROUND_COLUMN_PLAYER3_TICKS, ROUND_COLUMN_PLAYER4_TICKS, ROUND_COLUMN_UNDERDOG, ROUND_COLUMN_HEARTROUND)
        val args = arrayOf<String>(gameID.toString())

        val query = "$ROUND_COLUMN_GAME_ID like ?"
        return dbRead.query(ROUND_TABLE_NAME, projection, query, args, null, null, null )
    }


    private fun getCursorRound(roundId: Int) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val projection =  arrayOf<String>(ROUND_COLUMN_ID, ROUND_COLUMN_PLAYER1_TICKS, ROUND_COLUMN_PLAYER2_TICKS, ROUND_COLUMN_PLAYER3_TICKS, ROUND_COLUMN_PLAYER4_TICKS, ROUND_COLUMN_UNDERDOG, ROUND_COLUMN_HEARTROUND)
        val args = arrayOf<String>(roundId.toString())

        val query = "$ROUND_COLUMN_ID like ?"
        return dbRead.query(ROUND_TABLE_NAME, projection, query, args, null, null, null )
    }

    fun getRound(roundId : Int) : RoundObject
    {
        val cursor = getCursorRound(roundId)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            val result = RoundObject(cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER1_TICKS)),
                    cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER2_TICKS)),
                    cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER3_TICKS)),
                    cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER4_TICKS)),
                    cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_UNDERDOG)),
                    cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_HEARTROUND)))

            return result
        }
        throw Exception("game not found")

    }

    fun updateRound(roundId : Int, ro : RoundObject)
    {
        val cv = ContentValues()
        cv.put(ROUND_COLUMN_PLAYER1_TICKS, ro.p1)
        cv.put(ROUND_COLUMN_PLAYER2_TICKS, ro.p2)
        cv.put(ROUND_COLUMN_PLAYER3_TICKS, ro.p3)
        cv.put(ROUND_COLUMN_PLAYER4_TICKS, ro.p4)
        cv.put(ROUND_COLUMN_UNDERDOG, ro.ud)
        cv.put(ROUND_COLUMN_HEARTROUND, ro.hr)

        val args = arrayOf<String>(roundId.toString())

        val dbRead = DataBaseHandler(appContext).writableDatabase
        dbRead.update(ROUND_TABLE_NAME, cv, "$ROUND_COLUMN_ID = ?", args)
    }



    //fun getRounds(gameID: Long):

}
