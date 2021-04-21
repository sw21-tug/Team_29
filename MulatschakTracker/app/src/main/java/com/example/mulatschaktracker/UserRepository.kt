package com.example.mulatschaktracker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class UserRepository (var context: Context) {


    fun createUser(User: UserObject) : Long {
        val cursor = getCursor(User.name)
        if (cursor.count == 0) {
            val dbWrite = DataBaseHandler(context).writableDatabase
            val values = ContentValues()
            values.put("name",User.name)
            return dbWrite.insert("user", null, values)

        }
        throw Exception("username already taken")
    }

    fun resetDatabase() {
       val db =  DataBaseHandler(context).writableDatabase
        DataBaseHandler(context).onUpgrade(db, 0, 0)
    }

    fun getUser(UserName: String) : UserObject {
        val cursor = getCursor(UserName)
        if (cursor.count == 1) {
            val result = UserObject(cursor.getString(
                cursor.getColumnIndex("name")))
            result.id = cursor.getInt(
                cursor.getColumnIndex("id"))
            return result;
        }
        throw Exception("user not found")
    }

    private fun getCursor(UserName: String) : Cursor {
        if (UserName.isBlank() || UserName.isBlank()) {
             throw Exception("username is empty");
        }
        val dbRead = DataBaseHandler(context).readableDatabase
        val projection =  arrayOf<String>("id", "name")
        val args = arrayOf<String>(UserName)

        val query = "name like ?"
        return dbRead.query("user", projection, query,args, null, null, null )
    }

}
