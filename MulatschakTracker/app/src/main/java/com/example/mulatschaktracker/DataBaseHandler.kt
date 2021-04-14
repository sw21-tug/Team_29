package com.example.mulatschaktracker

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


val DATABASENAME = "MulaTschakTracker";

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,1)
        {

            fun createUser(User: UserObject) : Int {
                TODO("Not yet implemented")

            }

            fun  getUser(UserName: String) : UserObject {
                TODO("Not yet implemented")
             }

            override fun onCreate(db: SQLiteDatabase?) {
                TODO("Not yet implemented")
            }

            override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
                TODO("Not yet implemented")
            }
        }
