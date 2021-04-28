package com.example.mulatschaktracker

import android.content.Intent
import android.os.Bundle

import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mulatschaktracker.ui.createUser.CreateUserActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


val PREFERENCENAME = "muli"
val LASTUSER = "lastuser"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = getSharedPreferences(PREFERENCENAME, MODE_PRIVATE)
        val lastUserName = preferences.getString(LASTUSER, "")

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_statistic,R.id.navigation_History, R.id.navigation_Tipps, R.id.navigation_Options))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if ("".equals(lastUserName)) {
            val createUserIntent = Intent(this, CreateUserActivity::class.java);
            startActivity(createUserIntent);
        }
    }


    fun startNewGameActivity(view: View) {
        val intent = Intent(this, StartNewGame::class.java)
        startActivity(intent)
    }
}

