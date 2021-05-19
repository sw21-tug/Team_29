package com.example.mulatschaktracker

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASENAME = "MulaTschakTracker";
val DATABASE_VERSION = 2
val PERSON_TABLE_NAME = "user"
val PERSON_COLUMN_ID = "id"
val PERSON_COLUMN_NAME = "name"
val GAME_TABLE_NAME = "games"
val GAME_COLUMN_ID = "game_id"
val GAME_COLUMN_USER_ID = "user_id"
val GAME_COLUMN_PLAYER1 = "player_1"
val GAME_COLUMN_PLAYER2 = "player_2"
val GAME_COLUMN_PLAYER3 = "player_3"
val GAME_COLUMN_PLAYER4 = "player_4"
val ROUND_TABLE_NAME = "rounds"
val ROUND_COLUMN_ID = "round_id"
val ROUND_COLUMN_GAME_ID = "game_id"
val ROUND_COLUMN_PLAYER1_TICKS = "player1"
val ROUND_COLUMN_PLAYER2_TICKS = "player2"
val ROUND_COLUMN_PLAYER3_TICKS = "player3"
val ROUND_COLUMN_PLAYER4_TICKS = "player4"
val ROUND_COLUMN_UNDERDOG = "underdog"
val ROUND_COLUMN_HEARTROUND = "heartround"
val WINNER_TABLE_NAME = "winners"
val WINNER_COLUMN_ID = "winners_id"
val FIRST_WINNER_COLUMN = "first winner"
val SECOND_WINNER_COLUMN = "second winner"
val THIRD_WINNER_COLUMN = "third winner"
val FOURTH_WINNER_COLUMN = "fourth winner"
val GAME_IS_FINISHED = "game finished"


class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,DATABASE_VERSION)
        {

            override fun onCreate(db: SQLiteDatabase?) {
                db?.execSQL("CREATE TABLE " + PERSON_TABLE_NAME + " (" +
                        PERSON_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PERSON_COLUMN_NAME + " TEXT)")

                db?.execSQL("CREATE TABLE " + GAME_TABLE_NAME + " (" +
                        GAME_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        GAME_COLUMN_USER_ID + " INTEGER," +
                        GAME_COLUMN_PLAYER1 + " TEXT," +
                        GAME_COLUMN_PLAYER2 + " TEXT," +
                        GAME_COLUMN_PLAYER3 + " TEXT," +
                        GAME_COLUMN_PLAYER4 + " TEXT" +
                        GAME_IS_FINISHED + "BOOLEAN" +
                        ")")

                db?.execSQL("CREATE TABLE " + ROUND_TABLE_NAME + " (" +
                        ROUND_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ROUND_COLUMN_GAME_ID + " INTEGER," +
                        ROUND_COLUMN_PLAYER1_TICKS + " INTEGER," +
                        ROUND_COLUMN_PLAYER2_TICKS + " INTEGER," +
                        ROUND_COLUMN_PLAYER3_TICKS + " INTEGER," +
                        ROUND_COLUMN_PLAYER4_TICKS + " INTEGER," +
                        ROUND_COLUMN_UNDERDOG + " INTEGER," +
                        ROUND_COLUMN_HEARTROUND + " INTEGER" +
                        ")")

                db?.execSQL("CREATE TABLE " + WINNER_TABLE_NAME + " (" +
                        WINNER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        FIRST_WINNER_COLUMN + " TEXT" +
                        SECOND_WINNER_COLUMN + "TEXT" +
                        THIRD_WINNER_COLUMN + "TEXT" +
                        FOURTH_WINNER_COLUMN + "TEXT" +
                        ")")

            }

            override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
                db?.execSQL("DROP TABLE IF EXISTS " + PERSON_TABLE_NAME);
                db?.execSQL("DROP TABLE IF EXISTS " + GAME_TABLE_NAME);
                db?.execSQL("DROP TABLE IF EXISTS " + ROUND_TABLE_NAME);
                db?.execSQL("DROP TABLE IF EXISTS " + WINNER_TABLE_NAME);
                onCreate(db);
            }
        }
