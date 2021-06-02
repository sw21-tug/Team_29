package com.example.mulatschaktracker

class GameObject(player1: String, player2: String, player3: String, player4: String) : ArrayList<String>() {
    var id: Long = -1
    var player1: String = player1
    var player2: String = player2
    var player3: String = player3
    var player4: String = player4
    var finished: Int = 0
    var gamemode: Int = 0
    var player1won: Int = 0
    var player2won: Int = 0
    var player3won: Int = 0
    var player4won: Int = 0
    var rounds: List<RoundObject> = emptyList()
    var filter: GameRepository.Filter = GameRepository.Filter.DEFAULT


    fun setGameMode(data: Int)
    {
        this.gamemode =  data
    }


}
