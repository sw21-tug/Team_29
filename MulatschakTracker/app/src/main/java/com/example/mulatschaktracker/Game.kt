package com.example.mulatschaktracker


//import com.example.mulatschaktracker.ui.GameFinished.sendMessage

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.text.InputType.TYPE_CLASS_NUMBER
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.widget.*
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDeepLinkBuilder
import com.example.mulatschaktracker.ui.addGameRound.AddGameRoundActivity
import com.example.mulatschaktracker.ui.home.GameRecyclerAdapter.GameViewHolder.Companion.GAME_ID
import com.example.mulatschaktracker.ui.home.GameRecyclerAdapter.GameViewHolder.Companion.IS_FINISHED
import com.example.mulatschaktracker.ui.statistics.GameFinishedFragment
import kotlin.math.pow

class Game : AppCompatActivity() {

    private var mapOfResult = mapOf<Int, String>()
    private  var fragment : GameFinishedFragment?  = null

    private val layoutParams = TableRow.LayoutParams(
        TableRow.LayoutParams.WRAP_CONTENT,
        TableRow.LayoutParams.WRAP_CONTENT
    )

    private val textSize = 18F


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState
        )
        setContentView(R.layout.activity_game)


        val repository = GameRepository(this)

        val game = repository.getGame(intent.getLongExtra(GAME_ID, 0))

        findViewById<TextView>(R.id.textViewPlayer1).apply {
            text = game.player1
        }
        findViewById<TextView>(R.id.textViewPlayer2).apply {
            text = game.player2
        }
        findViewById<TextView>(R.id.textViewPlayer3).apply {
            text = game.player3
        }
        findViewById<TextView>(R.id.textViewPlayer4).apply {
            text = game.player4
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()

        if(intent.getIntExtra(IS_FINISHED,0) > 0) {
            val pendingIntent = NavDeepLinkBuilder(this.applicationContext)
                .setGraph(R.navigation.mobile_navigation)
                .setDestination(R.id.navigation_History)
                .createPendingIntent()

            pendingIntent.send()
        } else {
            var newIntent = Intent(this, MainActivity::class.java);
            startActivity(newIntent)
        }



    }

    @SuppressLint("ResourceType")
    override fun onResume() {
        super.onResume()

        val gameId: Long = intent.getLongExtra(GAME_ID, 0)
        val repository = GameRepository(this)
        val gameFinished = intent.getIntExtra(IS_FINISHED,0)

        val game = repository.getGame(gameId)

        var score_p1: Int = 21
        var score_p2: Int = 21
        var score_p3: Int = 21
        var score_p4: Int = 21
        // text size in sp





        val tableLayout = findViewById<TableLayout>(R.id.GameTable)
        val childCount: Int = tableLayout.getChildCount()
        tableLayout.removeViews(1, childCount - 1)
        val newRow = TableRow(this)



        for (i in 0..3){

            val newText = TextView(this)
            newText.id = i + 1
            newText.inputType = TYPE_CLASS_NUMBER
            newText.text = 21.toString()
            newText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            newText.gravity = Gravity.CENTER
            newRow.addView(newText, layoutParams)
        }
        tableLayout!!.addView(newRow)



        var cursor = repository.getCursorRounds(intent.getLongExtra(GAME_ID, 0))

        var rowIdCounter: Int = 12000
        var idcounter: Int = 4
        if (cursor.moveToFirst()) {
            do {

                val nrow = TableRow(this)



                val underDogCount = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_UNDERDOG))
                val heartRound = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_HEARTROUND))


                score_p1 = addViewElement(nrow, score_p1, ROUND_COLUMN_PLAYER1_TICKS, cursor, underDogCount, heartRound, idcounter)
                idcounter = idcounter.plus(1)
                score_p2 = addViewElement(nrow, score_p2, ROUND_COLUMN_PLAYER2_TICKS, cursor, underDogCount, heartRound, idcounter)
                idcounter = idcounter.plus(1)
                score_p3 = addViewElement(nrow, score_p3, ROUND_COLUMN_PLAYER3_TICKS, cursor, underDogCount, heartRound, idcounter)
                idcounter = idcounter.plus(1)
                score_p4 = addViewElement(nrow, score_p4, ROUND_COLUMN_PLAYER4_TICKS, cursor, underDogCount, heartRound, idcounter)
                idcounter = idcounter.plus(1)



                if(underDogCount > 0){
                    val newTextUnderdog = TextView(this)
                    newTextUnderdog.inputType = TYPE_CLASS_NUMBER
                    if(underDogCount > 1){
                        newTextUnderdog.text = getString(R.string.dog_emoji,underDogCount.toString())
                    } else {
                        newTextUnderdog.text = getString(R.string.dog_emoji,"")
                    }

                    newTextUnderdog.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                    newTextUnderdog.gravity = Gravity.LEFT
                    nrow.addView(newTextUnderdog, layoutParams)
                }

                if(heartRound > 0){
                    val newTextHeart = TextView(this)
                    //newTextUnderdog.inputType = TYPE_CLASS_NUMBER
                    newTextHeart.text = getString(R.string.heart_round_active)

                    newTextHeart.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                    newTextHeart.gravity = Gravity.LEFT
                    nrow.addView(newTextHeart, layoutParams)
                }

                var rowId = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_ID))
                nrow.id = rowIdCounter

                rowIdCounter = rowIdCounter.plus(1)

                if(gameFinished == 0){
                    nrow.setOnLongClickListener{

                        val handler : Handler = Handler();
                        handler.postDelayed( Runnable {
                            editRound(rowId)
                        }, 400)

                        return@setOnLongClickListener true
                    }
                }

                tableLayout!!.addView(nrow)



            } while (cursor.moveToNext())


            if(gameFinished > 0) {
                val addRoundButton = findViewById<Button>(R.id.AddRoundButton)
                addRoundButton.visibility = GONE
            } else {
                var dataToPass : String? = null;

                var data : MutableList<String> = mutableListOf()
                dataToPass =  game.player1 + "#" +score_p1.toString()
                data.add(dataToPass)
                dataToPass =  game.player2 + "#" +score_p2.toString()
                data.add(dataToPass)
                dataToPass =  game.player3 + "#" +score_p3.toString()
                data.add(dataToPass)
                dataToPass =  game.player4 + "#" +score_p4.toString()
                data.add(dataToPass)

                if (score_p1 <= 0 || score_p2 <= 0 || score_p3 <= 0 ||score_p4 <= 0 ||
                    score_p1 >= 100 || score_p2 >= 100 || score_p3 >= 100 || score_p4 >= 100) {

                    setContentView(R.layout.activity_game_finished)
                    var todb =  calculateString(data)
                    val res1 = repository.setGameFinished(gameId)
                    val res2 = repository.writeWinnersToDB(todb, gameId)
                    var button: Button = findViewById(R.id.game_finished_back_button)
                    button.setOnClickListener(View.OnClickListener(){
                        val mainIntent = Intent(this, MainActivity::class.java);
                        this.finish()
                        startActivity(mainIntent);
                    })

                }
            }
        }
    }


    fun addRound(view: View){
        var gameID = intent.getLongExtra(GAME_ID, 0)

        val bundle = Bundle()
        bundle.putLong(GAME_ID, gameID)
        val intent = Intent(this, AddGameRoundActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }

    fun editRound(ro: Int){
        var gameID = intent.getLongExtra(GAME_ID, 0)
        val bundle = Bundle()
        bundle.putLong(GAME_ID, gameID)
        bundle.putInt(ROUND_COLUMN_ID, ro)
        val intent = Intent(this, AddGameRoundActivity::class.java).apply {
            putExtras(bundle)
        }
         startActivity(intent)
    }


    fun calculateString(arg : List<String>) : GameObject
    {
        var place1 = findViewById<TextView>(R.id.textView)
        var place2 = findViewById<TextView>(R.id.textView2)
        var place3 = findViewById<TextView>(R.id.textView3)
        var place4 = findViewById<TextView>(R.id.textView4)
        println(arg)
        var map : MutableMap<String, Int> = mutableMapOf()

        for( i in arg)
        {
             var name =  i.split('#').toTypedArray()
            map[name[0]] = name[1].toInt()
        }
        var sortedMap  = map.toList().sortedBy { (_, value) -> value}.toMap()
        var firstPlace =  ""
        var secondPlace = ""
        var thirdPlace = ""
        var fortPlace = ""
        var winners1 = ""
        var winners2 = ""
        var winners3= ""
        var winners4 = ""

        var itr : Int =  0
        var sortedValue = sortedMap.values
        for(i in sortedMap)
        {
            if(i.value <= sortedValue.first())
            {
                firstPlace =  firstPlace +  i.key + ' '
                if(winners1 == "")
                {
                    println(i.key)
                    winners1 = i.key
                    continue
                }
                if(winners2 == "")
                {
                    println(i.key)

                    winners2 = i.key
                    continue

                }
                if(winners3 == "")
                {
                    println(i.key)

                    winners3 = i.key
                    continue

                }
                if(winners4 == "")
                {
                    println(i.key)

                    winners4 = i.key
                    continue

                }
                continue
            }
            if (secondPlace == "")
            {
                secondPlace = i.key
                continue
            }
            if (thirdPlace == "")
            {
                thirdPlace = i.key
                continue
            }
            if (fortPlace == "")
            {
                fortPlace = i.key
                continue
            }
        }


        place1?.setText("1. Place $firstPlace")
        place2?.setText("2. Place $secondPlace")
        place3?.setText("3. Place $thirdPlace")
        place4?.setText("4. Place $fortPlace")

        var retval = GameObject(winners1, winners2,winners3,winners4)
        return retval
    }

    fun calcScore(current: Int, tricks: Int, UnderDog: Int, HeartRound: Int) : Int
    {
        var deduction:Int
        var scoreMultiplicatorDog =  2.0f
        var scoreMultiplicatorHeart =  2.0f
        scoreMultiplicatorDog = scoreMultiplicatorDog.pow(UnderDog)
        scoreMultiplicatorHeart = scoreMultiplicatorHeart.pow(HeartRound)
        var scoreFactor = scoreMultiplicatorDog.toInt() * scoreMultiplicatorHeart.toInt()

        if(tricks == -1)
        {
            deduction = 2
        }else if (tricks == 0)
        {
            deduction = 5
        }
        else
        {
            deduction = tricks * -1
        }

        deduction = deduction * scoreFactor

        return (current + deduction)
    }

    fun addViewElement(nRow: TableRow, score: Int, columnIndex: String, cursor: Cursor, underDogCount: Int, heartRound: Int, idcounter: Int): Int {
        val tricksP4 = cursor.getInt(cursor.getColumnIndex(columnIndex))
        val newScore = calcScore(score, tricksP4, underDogCount, heartRound)

        val newText = TextView(this)
        newText.id = idcounter + 1
        newText.inputType = TYPE_CLASS_NUMBER
        newText.text = newScore.toString()
        newText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        newText.gravity = Gravity.CENTER
        nRow.addView(newText, layoutParams)
        return newScore

    }


}

