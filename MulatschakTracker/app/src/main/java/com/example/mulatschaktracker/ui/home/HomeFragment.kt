package com.example.mulatschaktracker.ui.home

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mulatschaktracker.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        homeViewModel =
//                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_games, container, false)
        val textView: TextView = root.findViewById(R.id.text_game)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        recyclerView = root.findViewById(R.id.gamesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        val userRepository = UserRepository(root.context)
        val gameRepository = GameRepository(root.context)
        val preferences = this.activity?.getSharedPreferences(PREFERENCENAME, MODE_PRIVATE)
        val userName = preferences?.getString(LASTUSER, "")
        var gameList: List<GameObject> = ArrayList()
        if(!userName.equals("")){

            try {
                val user = userName?.let { userRepository.getUser(it) }
                gameList = user?.let { gameRepository.getGames(it.id) }!!
            } catch (e: Exception) {
            }
        }
        if(gameList.isNotEmpty()){
            textView.text = ""
        } else {
            textView.text = root.context.getString(R.string.no_running_games)
        }
        recyclerView.adapter = GameRecyclerAdapter(gameList as ArrayList<GameObject>)
        return root
    }




}