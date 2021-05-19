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
        values.put(GAME_IS_FINISHED, newGameObject.finished)

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

    fun setGameFinished(gameID: Long): Int
    {
        val dbWrite = DataBaseHandler(appContext).writableDatabase
        val values = ContentValues()
        values.put(GAME_IS_FINISHED, 1)
        var arr =  arrayOf<String>(gameID.toString())
         return dbWrite.update(GAME_TABLE_NAME,values, GAME_COLUMN_ID + " = ?", arr)

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
            result.finished = cursor.getInt(
                    cursor.getColumnIndex(GAME_IS_FINISHED))
            return result
        }
        throw Exception("game not found")
    }

    private fun getCursor(gameID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val projection =  arrayOf<String>(GAME_COLUMN_ID, GAME_COLUMN_PLAYER1, GAME_COLUMN_PLAYER2, GAME_COLUMN_PLAYER3, GAME_COLUMN_PLAYER4, GAME_IS_FINISHED)
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

    fun getGameFinished(gameID: Long) : Int
    {
        var cursor =  getCursor(gameID)
         cursor.moveToFirst()
         return cursor.getInt(cursor.getColumnIndex(GAME_IS_FINISHED))
    }

    fun getWinners(gameId: Long) : GameObject
    {
        val cursor = getCursorWinners(gameId)
        if (cursor.count == 1) {
            cursor.moveToFirst()
            val result = GameObject(cursor.getString(cursor.getColumnIndex(FIRST_WINNER_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(SECOND_WINNER_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(THIRD_WINNER_COLUMN)),
                    cursor.getString(cursor.getColumnIndex(FOURTH_WINNER_COLUMN)))
            result.id = cursor.getLong(
                    cursor.getColumnIndex(GAME_COLUMN_ID))
            return result
        }
        throw Exception("winners not found")
    }

    fun getCursorRounds(gameID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val projection =  arrayOf<String>(ROUND_COLUMN_ID, ROUND_COLUMN_PLAYER1_TICKS, ROUND_COLUMN_PLAYER2_TICKS, ROUND_COLUMN_PLAYER3_TICKS, ROUND_COLUMN_PLAYER4_TICKS)
        val args = arrayOf<String>(gameID.toString())

        val query = "SELECT * FROM " + ROUND_TABLE_NAME + " WHERE " + ROUND_COLUMN_GAME_ID + " = " + gameID
        return dbRead.rawQuery(query, null )
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

    fun getLastRound(gameID: Long) : RoundObject
    {
        //var midle = gameID - 1
        var result = getCursor2( gameID)
        // TODO FOR 15 Points
       var player1Points = 21
        var player2Points = 21
        var player3Points = 21
        var player4Points = 21
        for(i in 0 .. result.count - 1  )
            {
                result.move(1)
                player1Points = calcScore(player1Points, result.getInt(result.getColumnIndex(ROUND_COLUMN_PLAYER1_TICKS)))
                player2Points = calcScore(player2Points ,result.getInt(result.getColumnIndex(ROUND_COLUMN_PLAYER2_TICKS)))
                player3Points = calcScore(player3Points , result.getInt(result.getColumnIndex(ROUND_COLUMN_PLAYER3_TICKS)))
                player4Points = calcScore(player4Points , result.getInt(result.getColumnIndex(ROUND_COLUMN_PLAYER4_TICKS)))
            }

        var round  = RoundObject(player1Points,player2Points, player3Points, player4Points,0,0)

        println(player1Points)
        println(player2Points)
        println(player3Points)
        println(player4Points)

        return round
    }

    fun writeWinnersToDB(newGameObject: GameObject)  : Long {

        val dbWrite = DataBaseHandler(appContext).writableDatabase
        val values = ContentValues()

        values.put(FIRST_WINNER_COLUMN, newGameObject.player1won)
        values.put(SECOND_WINNER_COLUMN, newGameObject.player2won)
        values.put(THIRD_WINNER_COLUMN, newGameObject.player3won)
        values.put(FOURTH_WINNER_COLUMN, newGameObject.player4won)

        return dbWrite.insert(WINNER_TABLE_NAME, null, values)
    }

    fun getCursorWinners(gameID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val projection =  arrayOf<String>(GAME_COLUMN_ID, FIRST_WINNER_COLUMN, SECOND_WINNER_COLUMN, THIRD_WINNER_COLUMN, FOURTH_WINNER_COLUMN)
        val args = arrayOf<String>(gameID.toString())

        val query = "$GAME_COLUMN_ID like ?"
        return dbRead.query(GAME_TABLE_NAME, projection, query, args, null, null, null )
    }
}
