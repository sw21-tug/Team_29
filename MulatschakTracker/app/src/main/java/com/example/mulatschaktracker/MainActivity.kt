package com.example.mulatschaktracker

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle

import android.view.View
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mulatschaktracker.ui.createUser.CreateUserActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


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

        //val btnclicked : Button = findViewById(R.id.buttonChangeLanguage)
        //btnclicked.setOnClickListener { DialogChangeLanguage() }


    }


    fun startNewGameActivity(view: View) {
        val intent = Intent(this, StartNewGame::class.java)
        startActivity(intent)
    }

    fun DialogChangeLanguage(view: View){

        val adb = AlertDialog.Builder(this)
        val items = arrayOf<CharSequence>(
            getString(R.string.eng_Lang),
            getString(R.string.ru_Lang))

        adb.setSingleChoiceItems(items, -1, DialogInterface.OnClickListener { arg0, arg1 ->
            if(arg1 == 0)
                setLanguage("en")
            else if (arg1 == 1)
                setLanguage("ru")
            //add more languages if needed
        })
        adb.setPositiveButton("OK",  DialogInterface.OnClickListener { arg0, arg1 ->
            //refresh application screen
            recreate()
        })
        adb.setTitle(getString(R.string.chooseLanguageTxt))
        adb.show()
    }

    private fun setLanguage(newLang: String){
        val locale = Locale(newLang)
        Locale.setDefault(locale)
        val config = Configuration()
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", newLang)
        editor.apply()
    }

    private fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "en")
        if (language != null) {
            setLanguage(language)
        }

    }

}

