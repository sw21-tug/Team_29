package com.example.mulatschaktracker

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


val DATABASENAME = "MulaTschakTracker";
val DATABASE_VERSION = 2


class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,DATABASE_VERSION)
        {

            val PERSON_TABLE_NAME = "user"
            val PERSON_COLUMN_ID = "id"
            val PERSON_COLUMN_NAME = "name"

            override fun onCreate(db: SQLiteDatabase?) {
                db?.execSQL("CREATE TABLE " + PERSON_TABLE_NAME + " (" +
                        PERSON_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PERSON_COLUMN_NAME + " TEXT)")

            }

            override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
                db?.execSQL("DROP TABLE IF EXISTS " + PERSON_TABLE_NAME);
                onCreate(db);
            }
        }
