package com.example.mulatschaktracker

class GameObject(player1: String, player2: String, player3: String, player4: String) : ArrayList<String>() {
    var id: Long = -1
    var player1: String = player1
    var player2: String = player2
    var player3: String = player3
    var player4: String = player4
    var rounds: List<RoundObject> = emptyList()
}
