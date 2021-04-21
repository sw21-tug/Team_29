package com.example.mulatschaktracker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class UserRepository (var context: Context) {

    /**
     * Method to create a UserObject.
     * If username is already in database throws an exception
     */
    fun createUser(User: UserObject) : Long {
        val cursor = getCursor(User.name)
        if (cursor.count == 0) {
            val dbWrite = DataBaseHandler(context).writableDatabase
            val values = ContentValues()
            values.put(PERSON_COLUMN_NAME,User.name)
            return dbWrite.insert(PERSON_TABLE_NAME, null, values)
        }
        throw Exception("username already taken")
    }

    /**
     *  Drops and recreates the whole database
     *  Use with CAUTION!!!
     */
    fun resetDatabase() {
       val db =  DataBaseHandler(context).writableDatabase
        DataBaseHandler(context).onUpgrade(db, 0, 0)
    }

    /**
     * Checks if the name is in the database and
     * returns an UserObject containing the information.
     * If an empty, blank or name that is not in the
     * database is supplied, the method fails with an
     * exception.
     */
    fun getUser(UserName: String) : UserObject {
        val cursor = getCursor(UserName)
        if (cursor.count == 1) {
            cursor.moveToFirst()
            val result = UserObject(cursor.getString(
                cursor.getColumnIndex(PERSON_COLUMN_NAME)))
            result.id = cursor.getLong(
                cursor.getColumnIndex(PERSON_COLUMN_ID))
            return result;
        }
        throw Exception("user not found")
    }

    private fun getCursor(UserName: String) : Cursor {
        if (UserName.isBlank() || UserName.isBlank()) {
             throw Exception("username is empty");
        }
        val dbRead = DataBaseHandler(context).readableDatabase
        val projection =  arrayOf<String>(PERSON_COLUMN_ID, PERSON_COLUMN_NAME)
        val args = arrayOf<String>(UserName)

        val query = PERSON_COLUMN_NAME +" like ?"
        return dbRead.query(PERSON_TABLE_NAME, projection, query,args, null, null, null )
    }

}
