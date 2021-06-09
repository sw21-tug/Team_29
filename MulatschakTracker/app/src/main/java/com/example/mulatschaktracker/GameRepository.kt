package com.example.mulatschaktracker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class GameRepository(var appContext: Context) {

    private val projection = arrayOf<String>(GAME_COLUMN_ID, GAME_COLUMN_PLAYER1, GAME_COLUMN_PLAYER2,
        GAME_COLUMN_PLAYER3, GAME_COLUMN_PLAYER4, GAME_IS_FINISHED, FILTER, FIRST_WINNER_COLUMN, SECOND_WINNER_COLUMN,
        THIRD_WINNER_COLUMN, FOURTH_WINNER_COLUMN, GAME_MODE)

    enum class Filter {
        WON, LOST, LOST_OVER100, WON_OVER100, DEFAULT
    }

    enum class Filter {
        WON, LOST, LOST_OVER100, WON_OVER100, DEFAULT
    }

    fun createGame(newGameObject: GameObject, userID: Long): Long {

        val dbWrite = DataBaseHandler(appContext).writableDatabase
        val values = ContentValues()
        values.put(GAME_COLUMN_PLAYER1, newGameObject.player1)
        values.put(GAME_COLUMN_PLAYER2, newGameObject.player2)
        values.put(GAME_COLUMN_PLAYER3, newGameObject.player3)
        values.put(GAME_COLUMN_PLAYER4, newGameObject.player4)
        values.put(GAME_IS_FINISHED, newGameObject.finished)
        values.put(FILTER, newGameObject.filter.ordinal)
        values.put(FIRST_WINNER_COLUMN, "")
        values.put(SECOND_WINNER_COLUMN, "")
        values.put(THIRD_WINNER_COLUMN, "")
        values.put(FOURTH_WINNER_COLUMN, "")
        values.put(GAME_COLUMN_USER_ID, userID)
        values.put(GAME_MODE, newGameObject.gamemode);

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


    fun setGameFinished(gameID: Long): Int {
        val dbWrite = DataBaseHandler(appContext).writableDatabase
        val values = ContentValues()
        values.put(GAME_IS_FINISHED, 1)
        var arr = arrayOf<String>(gameID.toString())
        return dbWrite.update(GAME_TABLE_NAME, values, "$GAME_COLUMN_ID = ?", arr)

    }

    fun resetDatabase() {
        val db =  DataBaseHandler(appContext).writableDatabase
        DataBaseHandler(appContext).onUpgrade(db, 0, 0)

    }

    fun getGameMode(gameID: Long): Int
    {
        var result : Int = 0
        val cursor = getCursor(gameID)
        if (cursor.count == 1) {
            cursor.moveToFirst()
            result = cursor.getInt(cursor.getColumnIndex(GAME_MODE)) // maybe crash ?
        }
        return result
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

    fun getGames(userID: Long, finished: Boolean): List<GameObject> {
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
                game.filter = Filter.values()[cursor.getInt(cursor.getColumnIndex(FILTER))]

                if (!cursor.isNull(cursor.getColumnIndex(GAME_IS_FINISHED))) {
                    game.finished = cursor.getInt(cursor.getColumnIndex(GAME_IS_FINISHED))
                } else {
                    game.finished = 0
                }
                if (finished) {
                    if (game.finished > 0) {
                        gameList.add(game)
                    }
                }else {
                    if (game.finished == 0) {
                        gameList.add(game)
                    }
                }
            } while (cursor.moveToNext())
            return gameList
        }
        return gameList
    }

    private fun getCursorByUser(userID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val args = arrayOf<String>(userID.toString())

        val query = "$GAME_COLUMN_USER_ID like ?"
        return dbRead.query(GAME_TABLE_NAME, projection, query,args, null, null, null )
    }

    private fun getCursor(gameID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val args = arrayOf<String>(gameID.toString())
        val query = "$GAME_COLUMN_ID like ?"
        return dbRead.query(GAME_TABLE_NAME, projection, query, args, null, null, null )
    }


    private fun getCursor3(gameID: Long): Cursor
    {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val args = arrayOf<String>(gameID.toString())
        val query = "$GAME_COLUMN_ID like ?"
        return dbRead.query(GAME_TABLE_NAME, projection, query, args, null, null, null )
    }


    fun getCursorRounds(gameID: Long) : Cursor {

        val dbRead = DataBaseHandler(appContext).readableDatabase
        val roundProjection =  arrayOf<String>(ROUND_COLUMN_ID, ROUND_COLUMN_PLAYER1_TICKS, ROUND_COLUMN_PLAYER2_TICKS,
            ROUND_COLUMN_PLAYER3_TICKS, ROUND_COLUMN_PLAYER4_TICKS, ROUND_COLUMN_UNDERDOG, ROUND_COLUMN_HEARTROUND)
        val args = arrayOf<String>(gameID.toString())

        val query = "$ROUND_COLUMN_GAME_ID like ?"
        return dbRead.query(ROUND_TABLE_NAME, roundProjection, query, args, null, null, null )
    }

    fun getGameFinished(gameID: Long) : Int
    {
        var cursor =  getCursor3(gameID)
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
            result.finished = 1

            return result
        }
        throw Exception("winners not found")
    }



    private fun getCursorRound(roundId: Int) : Cursor {

        val dbRead = DataBaseHandler(appContext).readableDatabase
        val roundProjection =  arrayOf<String>(ROUND_COLUMN_ID, ROUND_COLUMN_PLAYER1_TICKS, ROUND_COLUMN_PLAYER2_TICKS, ROUND_COLUMN_PLAYER3_TICKS, ROUND_COLUMN_PLAYER4_TICKS, ROUND_COLUMN_UNDERDOG, ROUND_COLUMN_HEARTROUND)
        val args = arrayOf<String>(roundId.toString())

        val query = "$ROUND_COLUMN_ID like ?"
        return dbRead.query(ROUND_TABLE_NAME, roundProjection, query, args, null, null, null )
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

   /* fun getLastRound(gameID: Long) : RoundObject
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


        return round
    }*/

    fun writeWinnersToDB(newGameObject: GameObject, gameID: Long) : Int {

        val dbWrite = DataBaseHandler(appContext).writableDatabase
        val values = ContentValues()
        println(newGameObject.player1)
        println(newGameObject.player2)
        var arr =  arrayOf<String>(gameID.toString())
        values.put(FIRST_WINNER_COLUMN, newGameObject.player1)
        values.put(SECOND_WINNER_COLUMN, newGameObject.player2)
        values.put(THIRD_WINNER_COLUMN, newGameObject.player3)
        values.put(FOURTH_WINNER_COLUMN, newGameObject.player4)

        return dbWrite.update(GAME_TABLE_NAME,values, "$GAME_COLUMN_ID = ?", arr)
    }

    fun getCursorWinners(gameID: Long) : Cursor {
        val dbRead = DataBaseHandler(appContext).readableDatabase
        val winnerProjection =  arrayOf<String>(GAME_COLUMN_ID, FIRST_WINNER_COLUMN, SECOND_WINNER_COLUMN,
            THIRD_WINNER_COLUMN, FOURTH_WINNER_COLUMN)
        val args = arrayOf<String>(gameID.toString())

        val query = "$GAME_COLUMN_ID like ?"
        return dbRead.query(GAME_TABLE_NAME, winnerProjection, query, args, null, null, null )
    }

    fun setFilter(filter : Filter, gameID: Long) : Int {
        val dbWrite = DataBaseHandler(appContext).writableDatabase
        val values = ContentValues()
        values.put(FILTER,filter.ordinal)
        var arr =  arrayOf<String>(gameID.toString())
        return dbWrite.update(GAME_TABLE_NAME,values, "$GAME_COLUMN_ID = ?", arr)
    }

    fun getGamesWon(userID: Long): List<GameObject> {

        var gameList : List<GameObject> = getGames(userID,finished = true)
        var gamesWonList : MutableList<GameObject> = ArrayList()
        for (game in gameList){
            if(game.filter == Filter.WON || game.filter == Filter.WON_OVER100){
                gamesWonList.add(game)
            }
        }
        return gamesWonList
    }

    fun getGamesLost(userID: Long): List<GameObject> {

        var gameList : List<GameObject> = getGames(userID,finished = true)
        var gamesWonList : MutableList<GameObject> = ArrayList()
        for (game in gameList){
            if(game.filter == Filter.LOST || game.filter == Filter.LOST_OVER100){
                gamesWonList.add(game)
            }
        }
        return gamesWonList
    }

    fun getGamesOver100(userID: Long): List<GameObject> {

        var gameList : List<GameObject> = getGames(userID,finished = true)
        var gamesWonList : MutableList<GameObject> = ArrayList()
        for (game in gameList){
            if(game.filter == Filter.WON_OVER100 || game.filter == Filter.LOST_OVER100){
                gamesWonList.add(game)
            }
        }
        return gamesWonList
    }
}
