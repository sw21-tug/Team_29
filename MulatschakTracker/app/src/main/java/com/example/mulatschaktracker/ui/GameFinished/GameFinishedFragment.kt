package com.example.mulatschaktracker.ui.statistics
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mulatschaktracker.R
//import com.example.mulatschaktracker.ui.GameFinished.sendMessage


    class GameFinishedFragment : Fragment() {
    //private var data = mapOf<Int, String>()
    private lateinit var gameFinishedViewModel: GameFinishedViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        gameFinishedViewModel =
                ViewModelProvider(this).get(GameFinishedViewModel::class.java)
        val root = inflater.inflate(R.layout.activity_game_finished, container, false)
        val textView: TextView = root.findViewById(R.id.Game_Finished)
        gameFinishedViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

   }