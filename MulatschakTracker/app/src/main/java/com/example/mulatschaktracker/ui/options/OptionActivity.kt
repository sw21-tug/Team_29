package com.example.mulatschaktracker.ui.options

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mulatschaktracker.R
import java.util.*

class OptionActivity :  AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.fragment_options)
            val btnclicked :Button = findViewById(R.id.buttonChangeLanguage)

            btnclicked.setOnClickListener { DialogChangeLanguage() }
        }

     fun DialogChangeLanguage(){


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