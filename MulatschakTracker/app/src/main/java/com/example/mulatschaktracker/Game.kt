package com.example.mulatschaktracker


//import com.example.mulatschaktracker.ui.GameFinished.sendMessage
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.InputType.TYPE_CLASS_NUMBER
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.mulatschaktracker.ui.addGameRound.AddGameRoundActivity

import com.example.mulatschaktracker.ui.statistics.GameFinishedFragment
import org.w3c.dom.Text
import javax.xml.datatype.DatatypeFactory.newInstance
import javax.xml.parsers.DocumentBuilderFactory.newInstance
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.setupWithNavController

import com.example.mulatschaktracker.ui.createUser.CreateUserActivity

import com.example.mulatschaktracker.ui.home.GameRecyclerAdapter.GameViewHolder.Companion.GAME_ID
import kotlin.math.pow

class Game : AppCompatActivity() {

    private var mapOfResult = mapOf<Int, String>()
    private  var fragment : GameFinishedFragment?  = null


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
        val mainIntent = Intent(this, MainActivity::class.java);
        startActivity(mainIntent);
    }

    @SuppressLint("ResourceType")
    override fun onResume() {
        super.onResume()

        val gameId: Long = intent.getLongExtra(EXTRA_MESSAGE, 0)
        val repository = GameRepository(this)
        
        val game = repository.getGame(intent.getLongExtra(GAME_ID, 0))

        var score_p1: Int = 21
        var score_p2: Int = 21
        var score_p3: Int = 21
        var score_p4: Int = 21
        // text size in sp
        val textSize = 18F




        val tableLayout = findViewById<TableLayout>(R.id.GameTable)
        val childCount: Int = tableLayout.getChildCount()
        tableLayout.removeViews(1, childCount - 1)
        val newRow = TableRow(this)

        var layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        )

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

                var dataToPass : String? = null;

                var data : MutableList<String> = mutableListOf()



                score_p1 = calcScore(score_p1, cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER1_TICKS)))
                dataToPass =  game.player1 + "#" +score_p1.toString()
                data.add(dataToPass)

                val underDogCount = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_UNDERDOG))

                val tricksP1 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER1_TICKS))
                score_p1 = calcScore(score_p1, tricksP1,underDogCount)

                val newTextP1 = TextView(this)
                newTextP1.id = idcounter + 1
                newTextP1.inputType = TYPE_CLASS_NUMBER
                newTextP1.text = score_p1.toString()
                newTextP1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                newTextP1.gravity = Gravity.CENTER
                nrow.addView(newTextP1, layoutParams)
                idcounter = idcounter.plus(1)


                val tricksP2 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER2_TICKS))
                score_p2 = calcScore(score_p2, tricksP2, underDogCount)
                 dataToPass =  game.player2 + "#" +score_p2.toString()
                data.add(dataToPass)
                
                val newTextP2 = TextView(this)
                newTextP2.id = idcounter + 1
                newTextP2.inputType = TYPE_CLASS_NUMBER
                newTextP2.text = score_p2.toString()
                newTextP2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                newTextP2.gravity = Gravity.CENTER
                nrow.addView(newTextP2, layoutParams)
                idcounter = idcounter.plus(1)

                score_p3 = calcScore(score_p3, cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER3_TICKS)))
               
                val tricksP3 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER3_TICKS))
                score_p3 = calcScore(score_p3, tricksP3,underDogCount)
                 dataToPass =  game.player3 + "#" +score_p3.toString()
                data.add(dataToPass)

                val newTextP3 = TextView(this)
                newTextP3.id = idcounter + 1
                newTextP3.inputType = TYPE_CLASS_NUMBER
                newTextP3.text = score_p3.toString()
                newTextP3.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                newTextP3.gravity = Gravity.CENTER
                nrow.addView(newTextP3, layoutParams)
                idcounter = idcounter.plus(1)


                score_p4 = calcScore(score_p4, cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER4_TICKS)))
                


                val tricksP4 = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER4_TICKS))
                score_p4 = calcScore(score_p4, tricksP4, underDogCount)
                dataToPass =  game.player4 + "#" +score_p4.toString()
                data.add(dataToPass)

                val newTextP4 = TextView(this)
                newTextP4.id = idcounter + 1
                newTextP4.inputType = TYPE_CLASS_NUMBER
                newTextP4.text = score_p4.toString()
                newTextP4.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                newTextP4.gravity = Gravity.CENTER
                nrow.addView(newTextP4, layoutParams)
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

                var rowId = cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_ID))
                nrow.id = rowIdCounter

                rowIdCounter = rowIdCounter.plus(1)

                nrow.setOnLongClickListener{

                    val handler : Handler = Handler();
                    handler.postDelayed( Runnable {
                        editRound(rowId)
                    }, 400)

                    return@setOnLongClickListener true
                }



                if (score_p1 <= 0 || score_p2 <= 0 || score_p3 <= 0 ||score_p4 <= 0 ||
                        score_p1 >= 100 || score_p2 >= 100 || score_p3 >= 100 || score_p4 >= 100) {

                    setContentView(R.layout.activity_game_finished)
                    var todb =  calculateString(data)
                    repository.setGameFinished(gameId)
                    repository.writeWinnersToDB(todb, gameId)
                    var button: Button = findViewById(R.id.game_finished_back_button)
                    button.setOnClickListener(View.OnClickListener(){
                        val mainIntent = Intent(this, MainActivity::class.java);
                        this.finish()
                        startActivity(mainIntent);
                    })

                }

                tableLayout!!.addView(nrow)



            } while (cursor.moveToNext())
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

    fun calcScore(current: Int, tricks: Int, UnderDog: Int) : Int
    {
        var deduction:Int
        var scoreMultiplicator =  2.0f
        scoreMultiplicator = scoreMultiplicator.pow(UnderDog)
        var scoreFactor = scoreMultiplicator.toInt()

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


}

