package com.example.mulatschaktracker.ui.createUser

import android.content.DialogInterface
import android.os.Bundle
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.mulatschaktracker.*


class CreateUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        val createButton :Button = findViewById(R.id.SubmitUserNameButton);
        createButton.setOnClickListener {createUser()}

    }

    fun createUser() {
        val repo = UserRepository(this);
        try {
            val textView : EditText  = findViewById(R.id.UserNameInput);
            var username = textView.text.toString()
            var user = UserObject(username)
            val id = repo.createUser(user)
            val preferences = getSharedPreferences(PREFERENCENAME, MODE_PRIVATE)
            val res = preferences.edit().putString(LASTUSER, user.name).commit()
            if (!res) {
                throw Exception("can't write to preferences");
            }
        } catch (ex : Exception) {
            ex.message?.let { showErrorMessage(it) }
            return
        }
        val mainIntent = Intent(this, MainActivity::class.java);
        startActivity(mainIntent);
    }

    fun showErrorMessage(message : String) {
        val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
        builder1.setMessage(message)
        builder1.setCancelable(false)

        builder1.setPositiveButton(
            "Ok",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

      /*  builder1.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() }) */

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

}
