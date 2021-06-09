package com.example.mulatschaktracker.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mulatschaktracker.Game
import com.example.mulatschaktracker.GameObject
import com.example.mulatschaktracker.R


class GameRecyclerAdapter( private val games: ArrayList<GameObject>): RecyclerView.Adapter<GameRecyclerAdapter.GameViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.frame_textview, parent, false)
        return GameViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val gameItem = games[position]
        holder.bindGame(gameItem)
    }

    override fun getItemCount(): Int {
       return games.size
    }


    class GameViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        private var view = view
        private var game : GameObject? = null
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = itemView.context
            val showGameIntent = Intent(context, Game::class.java)
            showGameIntent.putExtra(GAME_ID, game?.id)
            showGameIntent.putExtra(IS_FINISHED, game?.finished)
            context.startActivity(showGameIntent)
        }

        fun bindGame(gameItem: GameObject){
            this.game = gameItem
            view.findViewById<TextView>(R.id.game_textview).text = view.context.getString(R.string.game,game?.id)
            view.findViewById<TextView>(R.id.game_textview_players).text = view.context.getString(R.string.players_signature,game?.player1,game?.player2,game?.player3,game?.player4)
        }

        fun getView(): Any? {
            return view
        }

        companion object {
            //5
            public val GAME_ID = "GAME_ID"
            public val IS_FINISHED = "IS_FINISHED"
        }
    }
}

