package com.example.mulatschaktracker.ui.options


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import com.example.mulatschaktracker.R
import com.example.mulatschaktracker.UserRepository

class OptionsFragment : Fragment() {

    private lateinit var optionsViewModel: OptionsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        optionsViewModel =
                ViewModelProvider(this).get(OptionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_options, container, false)
        return root
    }

    fun onDeleteUserPressed(view: View){

        val context = getActivity()
        val du = AlertDialog.Builder(context)

        du.setPositiveButton("Yes",  DialogInterface.OnClickListener { arg0, arg1 ->
            //refresh application screen

            val userRepo = context?.let { UserRepository(it) }
            if (userRepo != null) {
                userRepo.resetDatabase()
            }
        })
        du.setTitle(getString(R.string.alert_delete_user))
        du.show()
    }
}
