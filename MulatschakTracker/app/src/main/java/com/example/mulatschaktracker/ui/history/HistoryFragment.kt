package com.example.mulatschaktracker.ui.history


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mulatschaktracker.*
import com.example.mulatschaktracker.ui.home.GameRecyclerAdapter


class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        historyViewModel =
                ViewModelProvider(this).get(HistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_history, container, false)

        val textView: TextView = root.findViewById(R.id.text_history)
        recyclerView = root.findViewById(R.id.historyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        val userRepository = UserRepository(root.context)
        val gameRepository = GameRepository(root.context)
        val preferences = this.activity?.getSharedPreferences(PREFERENCENAME, Context.MODE_PRIVATE)
        val userName = preferences?.getString(LASTUSER, "")
        var gameList: List<GameObject> = ArrayList()
        var userID : Long = 0
        if(!userName.equals("")){

            try {
                val user = userName?.let { userRepository.getUser(it) }
                userID = user?.id!!
                gameList = user?.let { gameRepository.getGames(it.id, true) }!!
            } catch (e: Exception) {
            }
        }
        val radioGroup = root.findViewById<RadioGroup>(R.id.radioGroup_filter)



        if(gameList.isNotEmpty()){
            textView.text = ""
        } else {
            textView.text = root.context.getString(R.string.no_finished_games)
        }

        recyclerView.adapter = GameRecyclerAdapter(gameList as ArrayList<GameObject>)

        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener()
            { radioGroup, checkedId ->
                when (checkedId) {
                    R.id.radio_won -> {
                        gameList = gameRepository.getGamesWon(userID)
                        recyclerView.adapter = GameRecyclerAdapter(gameList as ArrayList<GameObject>)
                    }
                    R.id.radio_lost -> {
                        gameList = gameRepository.getGamesLost(userID)
                        recyclerView.adapter = GameRecyclerAdapter(gameList as ArrayList<GameObject>)
                    }
                    R.id.radio_100 -> {
                        gameList = gameRepository.getGamesOver100(userID)
                        recyclerView.adapter = GameRecyclerAdapter(gameList as ArrayList<GameObject>)
                    }
                    R.id.radio_all -> {
                        gameList = gameRepository.getGames(userID, true)
                        recyclerView.adapter = GameRecyclerAdapter(gameList as ArrayList<GameObject>)
                    }
                }
            })

        return root
    }
}
