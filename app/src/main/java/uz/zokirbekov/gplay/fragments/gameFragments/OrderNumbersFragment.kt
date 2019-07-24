package uz.zokirbekov.gplay.fragments.gameFragments

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.GridLayout
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import android.widget.Toast
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.fragments.BaseGameFragment
import uz.zokirbekov.gplay.utils.Point
import java.util.*

class OrderNumbersFragment : BaseGameFragment(), View.OnClickListener {

    lateinit var text_time: TextView
    var time:Int = 20000

    lateinit var gridLayout: GridLayout
    lateinit var map:MutableList<MutableList<AppCompatTextView>>
    var width = 100
    val game:Game = Game()

    val handler = Handler()
    val action:Runnable = object : Runnable {
        override fun run() {
            if (time <= 0) {
                Toast.makeText(context,"Time is over",Toast.LENGTH_LONG).show()
                gameOver()
                return
            }
            time -= 50
            text_time.text = (time/100).toString()
            handler.postDelayed(this,50)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_order_numbers, container, false)
        //game.newGame()
        calculateTextWidth()
        initViews(view)
        gameOver()
        //randomNumbersToText()
        //startTimer()
        return view
    }

    private fun initViews(v:View) {
        gridLayout = v.findViewById(R.id.gridLayout)
        text_time = v.findViewById(R.id.text_time)
        addTextViewsToGrid()
    }

    private fun newGame()
    {
        game.newGame()
        randomNumbersToText()
        startTimer()
    }

    private fun gameOver()
    {
        detachTextClicks()
        initGridClicks()
        map[1][1].text = "N"
        map[1][2].text = "E"
        map[1][3].text = "W"

        map[2][0].text = "G"
        map[2][1].text = "A"
        map[2][2].text = "M"
        map[2][3].text = "E"
        map[2][4].text = "?"
    }

    private fun initGridClicks()
    {
        gridLayout.setOnClickListener({
            newGame()
            detachGridClick()
            initTextClicks()
        })
    }
    private fun detachGridClick()
    {
        gridLayout.setOnClickListener(null)
    }

    private fun detachTextClicks()
    {
        for (i in 0..game.mapSize - 1)
        {
            for (j in 0..game.mapSize - 1)
            {
                map[i][j].setOnClickListener(null)
                map[i][j].isClickable = false
            }
        }
    }

    private fun initTextClicks()
    {
        for (i in 0..game.mapSize - 1)
        {
            for (j in 0..game.mapSize - 1)
            {
                map[i][j].isClickable = true
                map[i][j].setOnClickListener(this)
            }
        }
    }

    private fun addTextViewsToGrid()
    {

        gridLayout.removeAllViews()
        gridLayout.columnCount = game.mapSize
        gridLayout.rowCount = game.mapSize

        map = MutableList(game.mapSize, { MutableList<AppCompatTextView>(game.mapSize, { AppCompatTextView(context) })})



        for (i in 0..game.mapSize - 1)
        {
            for (j in 0..game.mapSize - 1)
            {
                map[i][j].width = width
                map[i][j].height = width
                map[i][j].background = ContextCompat.getDrawable(context!!,R.drawable.border_order_number)
                map[i][j].gravity = Gravity.CENTER
                map[i][j].setPadding(16,16,16,16)
                map[i][j].setOnClickListener(this)
                map[i][j].tag = Point(i,j)

                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(map[i][j],18,70,8,TypedValue.COMPLEX_UNIT_SP)

                val params = GridLayout.LayoutParams()
                params.setMargins(10,10,10,10)

                gridLayout.addView(map[i][j],params)

            }
        }
    }

    private fun randomNumbersToText()
    {
        for (i in 0..game.mapSize - 1)
        {
            for (j in 0..game.mapSize - 1)
            {
                map[i][j].text = game.map[i][j].toString()
            }
        }
    }

    fun startTimer()
    {
        time = 20000
        action.run()
    }

    fun stopTimer()
    {
        handler.removeCallbacks(action)
    }

    private fun calculateTextWidth()
    {
        val display = activity?.windowManager?.defaultDisplay
        width = (display?.width!! / game.mapSize) - 24
    }

    override fun onClick(v: View?) {

        val textView = v as AppCompatTextView
        val point = textView.tag as Point

        if (game.checkNumber(point.i,point.j))
        {
            textView.text = ""
        }
        if (game.isGameOver())
        {
            stopTimer()
            gameOver()
            Toast.makeText(context,"Game Over", Toast.LENGTH_LONG).show()
        }

    }

    class Game
   {
       val mapSize = 5

       lateinit var map:MutableList<MutableList<Int>>

       var currentNumber = 1

       fun newGame()
       {
           map = MutableList(mapSize, { MutableList<Int>(mapSize,{0}) })
           random()
           currentNumber = 1
       }

       fun isGameOver() : Boolean
       {
           return currentNumber == mapSize*mapSize + 1
       }

       fun checkNumber(i:Int,j:Int) : Boolean
       {
           val b = map[i][j] == currentNumber
           if (b) currentNumber++
           return b
       }

       private fun random()
       {
           var number = 1
           var i: Int
           var j: Int
           for (q in 0..mapSize - 1)
           {
               for (k in 0..mapSize - 1)
               {
                   do {

                       i = Random().nextInt(mapSize)
                       j = Random().nextInt(mapSize)

                   } while(map[i][j] != 0)
                   map[i][j] = number
                   number++
               }
           }
       }
   }
}