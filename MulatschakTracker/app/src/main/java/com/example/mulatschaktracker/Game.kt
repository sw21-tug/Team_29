package com.example.mulatschaktracker

//import com.example.mulatschaktracker.ui.GameFinished.sendMessage
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.InputType.TYPE_CLASS_NUMBER
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mulatschaktracker.ui.addGameRound.AddGameRoundActivity
import com.example.mulatschaktracker.ui.statistics.GameFinishedFragment
import org.w3c.dom.Text
import javax.xml.datatype.DatatypeFactory.newInstance
import javax.xml.parsers.DocumentBuilderFactory.newInstance


class Game : AppCompatActivity() {

    private var mapOfResult = mapOf<Int, String>()
    private  var fragment : GameFinishedFragment?  = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState
        )
        setContentView(R.layout.activity_game)


        val repository = GameRepository(this)
        val game = repository.getGame(intent.getLongExtra(EXTRA_MESSAGE, 0))
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


    @SuppressLint("ResourceType")
    override fun onResume() {
        super.onResume()

        val gameId: Long = intent.getLongExtra(EXTRA_MESSAGE, 0)
        val repository = GameRepository(this)
        val game = repository.getGame(gameId)
        var score_p1: Int = 21
        var score_p2: Int = 21
        var score_p3: Int = 21
        var score_p4: Int = 21




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
            newText.gravity = Gravity.CENTER
            newRow.addView(newText, layoutParams)
        }
        tableLayout!!.addView(newRow)

        var cursor = repository.getCursor2(gameId)

        var idcounter: Int = 4
        if (cursor.moveToFirst()) {
            do {
                val nrow = TableRow(this)
                var dataToPass : String? = null;

                var data : MutableList<String> = mutableListOf()

                score_p1 = calcScore(score_p1, cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER1_TICKS)))
                dataToPass =  game.player1 + "#" +score_p1.toString()
                data.add(dataToPass)
                val newTextP1 = TextView(this)
                newTextP1.id = idcounter + 1
                newTextP1.inputType = TYPE_CLASS_NUMBER
                newTextP1.text = score_p1.toString()
                newTextP1.gravity = Gravity.CENTER
                nrow.addView(newTextP1, layoutParams)
                idcounter = idcounter.plus(1)

                score_p2 = calcScore(score_p2, cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER2_TICKS)))
                dataToPass =  game.player2 + "#" +score_p2.toString()
                data.add(dataToPass)
                val newTextP2 = TextView(this)
                newTextP2.id = idcounter + 1
                newTextP2.inputType = TYPE_CLASS_NUMBER
                newTextP2.text = score_p2.toString()
                newTextP2.gravity = Gravity.CENTER
                nrow.addView(newTextP2, layoutParams)
                idcounter = idcounter.plus(1)

                score_p3 = calcScore(score_p3, cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER3_TICKS)))
                dataToPass =  game.player3 + "#" +score_p3.toString()
                data.add(dataToPass)

                val newTextP3 = TextView(this)
                newTextP3.id = idcounter + 1
                newTextP3.inputType = TYPE_CLASS_NUMBER
                newTextP3.text = score_p3.toString()
                newTextP3.gravity = Gravity.CENTER
                nrow.addView(newTextP3, layoutParams)
                idcounter = idcounter.plus(1)

                score_p4 = calcScore(score_p4, cursor.getInt(cursor.getColumnIndex(ROUND_COLUMN_PLAYER4_TICKS)))
                dataToPass =  game.player4 + "#" +score_p4.toString()
                data.add(dataToPass)

                val newTextP4 = TextView(this)
                newTextP4.id = idcounter + 1
                newTextP4.inputType = TYPE_CLASS_NUMBER
                newTextP4.text = score_p4.toString()
                newTextP4.gravity = Gravity.CENTER
                nrow.addView(newTextP4, layoutParams)
                idcounter = idcounter.plus(1)


                if (score_p1 <= 0 || score_p2 <= 0 || score_p3 <= 0 ||score_p4 <= 0 ||
                    score_p1 >= 100 || score_p2 >= 100 || score_p3 >= 100 || score_p4 >= 100) {

                    setContentView(R.layout.activity_game_finished)
                    tableLayout!!.addView(nrow)
                     var todb =  calculateString(data)
                    repository.setGameFinished(gameId)
                    repository.writeWinnersToDB(todb, gameId)
                    var button: Button = findViewById(R.id.game_finished_back_button)
                    button.setOnClickListener(View.OnClickListener(){
                      setContentView(R.layout.activity_main)

                    })

                }



            } while (cursor.moveToNext())
        }
    }


    fun addRound(view: View){
        var gameID = intent.getLongExtra(EXTRA_MESSAGE, 0)
        val intent = Intent(this, AddGameRoundActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, gameID)
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

    fun calcScore(current: Int, tricks: Int) : Int
    {
        var deduction:Int
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

        return current + deduction
    }


}

private fun Button.setOnClickListener() : Boolean {

    return true
}
