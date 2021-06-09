package com.example.mulatschaktracker

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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
        loadLocale()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = getSharedPreferences(PREFERENCENAME, MODE_PRIVATE)
        val lastUserName = preferences.getString(LASTUSER, "")

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_History, R.id
                .navigation_Options),   fallbackOnNavigateUpListener = ::onSupportNavigateUp)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setSupportActionBar(toolbar)

        if ("".equals(lastUserName)) {
            val createUserIntent = Intent(this, CreateUserActivity::class.java)
            startActivity(createUserIntent)
        }

        val username = findViewById<TextView>(R.id.userNameLabel)
        if (username != null) {
            username.text = lastUserName
        }
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

        adb.setSingleChoiceItems(items, -1) { arg0, arg1 ->
            if (arg1 == 0)
                setLanguage("en")
            else if (arg1 == 1)
                setLanguage("ru")
            //add more languages if needed
        }
        adb.setPositiveButton("OK") { arg0, arg1 ->
            //refresh application screen
            recreate()
        }
        adb.setTitle(getString(R.string.chooseLanguageTxt))
        adb.show()
    }

    fun onDeleteUserPressed(view: View){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.delete_user_button)
        val du = builder.create()
        du.setButton(DialogInterface.BUTTON_POSITIVE,getString(R.string.yes)
        ) { arg0, arg1 ->

            val userRepo = UserRepository(this)
            userRepo.resetDatabase()
            val preferences = getSharedPreferences(PREFERENCENAME, MODE_PRIVATE)
            preferences.edit().remove(LASTUSER).apply()
            val createUserIntent = Intent(this, CreateUserActivity::class.java)
            startActivity(createUserIntent)
        }
        du.setButton(DialogInterface.BUTTON_NEGATIVE,getString(R.string.cancel),
            DialogInterface.OnClickListener{
                arg0, arg1 -> })
        du.setMessage(getString(R.string.alert_delete_user))
        du.show()

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

