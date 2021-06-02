package com.example.mulatschaktracker.ui.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        if(!userName.equals("")){

            try {
                val user = userName?.let { userRepository.getUser(it) }
                gameList = user?.let { gameRepository.getGames(it.id, true) }!!
            } catch (e: Exception) {
            }
        }
        if(gameList.isNotEmpty()){
            textView.text = ""
        } else {
            textView.text = root.context.getString(R.string.no_finished_games)
        }
        recyclerView.adapter = GameRecyclerAdapter(gameList as ArrayList<GameObject>)
        return root
    }
}
