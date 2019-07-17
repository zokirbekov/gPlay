package uz.zokirbekov.gplay.fragments.gameFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.fragments.BaseGameFragment
import java.util.*

class OrderNumbersFragment : BaseGameFragment(){

    lateinit var gridLayout: GridLayout
    lateinit var map:MutableList<MutableList<TextView>>
    var width = 50
    val game:Game = Game()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_order_numbers, container, false)
        calculateTextWidth(v)
        initViews(v)
        initClicks()
        game.newGame()
        randomNumbersToText()
        return v
    }

    private fun initViews(v:View) {
        gridLayout = v.findViewById(R.id.gridLayout)
        gridLayout.columnCount = game.mapSize
        gridLayout.rowCount = game.mapSize
        addTextViewsToGrid()
    }

    private fun initClicks() {

    }

    private fun addTextViewsToGrid()
    {
        map = MutableList(game.mapSize, { MutableList<TextView>(game.mapSize, { TextView(context) })})



        for (i in 0..game.mapSize - 1)
        {
            for (j in 0..game.mapSize - 1)
            {
                map[i][j].width = width
                map[i][j].height = width
                map[i][j].textSize = 18f

                val params = GridLayout.LayoutParams()
                params.width = width
                params.height = width
                params.setMargins(5,5,5,5)

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


    private fun calculateTextWidth(v:View)
    {
        width = (v.width / game.mapSize) - 32
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
           return currentNumber == mapSize*mapSize
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