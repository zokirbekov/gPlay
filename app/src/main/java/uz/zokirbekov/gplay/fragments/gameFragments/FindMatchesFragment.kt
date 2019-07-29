package uz.zokirbekov.gplay.fragments.gameFragments

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.fragments.BaseGameFragment
import uz.zokirbekov.gplay.utils.AnimationHelper
import uz.zokirbekov.gplay.utils.Point
import java.util.*

class FindMatchesFragment : BaseGameFragment(), View.OnClickListener {

    val game:Game = Game()

    var width = 100
    var height = 100

    var isSelected = false
    var selectedButton:Button? = null

    lateinit var grid: GridLayout
    lateinit var map:MutableList<MutableList<Button>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_find_matches, container, false)

        map = MutableList(game.size, { MutableList<Button>(game.size, {Button(context) })})
        calcWidthAndHeight()
        initViews(v)
        newGame()
        return v
    }

    private fun initViews(v:View)
    {
        grid = v.findViewById(R.id.find_matches_grid)
        initMap()
    }

    private fun calcWidthAndHeight()
    {
        val display = activity?.windowManager?.defaultDisplay
        width = (display?.width!! / game.size) - 2*game.size - 2
        height = (display?.height!! / game.size) - 2*game.size - 16
    }

    private fun newGame()
    {
        game.newGame()
        initMap()
    }

    private fun gameOver()
    {

    }

    private fun initMap()
    {
        grid.removeAllViews()
        grid.rowCount = game.size
        grid.columnCount = game.size

        for (i in 0..game.size - 1)
            for (j in 0..game.size - 1)
            {
                map[i][j].height = height
                map[i][j].width = width
                map[i][j].setText(game.map[i][j].toString())
                map[i][j].setOnClickListener(this)
                map[i][j].setBackgroundColor(Color.BLACK)
                map[i][j].setTextColor(Color.WHITE)
                map[i][j].setTextSize(22f)

                val params = GridLayout.LayoutParams()

                params.setMargins(2,2,2,2)
                params.width = width
                params.height = height

                map[i][j].layoutParams = params

                grid.addView(map[i][j])
            }
    }

    override fun onClick(v: View?) {
        var b = v as Button
        if (!isSelected)
        {
            selectedButton = b
            isSelected = true
        }
        else
        {
            if (selectedButton != b) {
                var s1 = selectedButton?.text
                if (b.text.equals(s1)) {
                    AnimationHelper.fadeOut(b)
                    AnimationHelper.fadeOut(selectedButton!!)
                }
                isSelected = false
            }
        }
    }

    class Game
    {
        val size = 4
        val map = MutableList(size, { MutableList<Int>(size, {0})})
        private val random = Random()

        fun newGame()
        {
            randomMap()
        }

        private fun randomMap()
        {
            for (n in 1..size*size/2) {
                setValuetoMap(n)
                setValuetoMap(n)
            }
        }

        private fun setValuetoMap(n:Int)
        {
            var point = getRandomPoint()
            map[point.i][point.j] = n
        }

        private fun getRandomPoint() : Point
        {

            var i: Int
            var j: Int
            do {
                i = random.nextInt(size)
                j = random.nextInt(size)
            } while (map[i][j] != 0)
            return Point(i,j)
        }



    }
}