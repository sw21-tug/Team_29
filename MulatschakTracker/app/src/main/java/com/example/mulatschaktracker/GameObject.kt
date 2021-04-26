package com.example.mulatschaktracker

class GameObject(player1: String, player2: String, player3: String, player4: String) : ArrayList<String>() {
    var id: Long = -1
    var player1: String = ""
    var player2: String = ""
    var player3: String = ""
    var player4: String = ""
    var rounds: List<RoundObject> = emptyList()
}
