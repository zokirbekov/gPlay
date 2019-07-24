package uz.zokirbekov.gplay.fragments.gameFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.GridLayout
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.fragments.BaseGameFragment
import uz.zokirbekov.gplay.utils.AnimationHelper
import uz.zokirbekov.gplay.utils.Point

class TicTacToeFragment : BaseGameFragment(), View.OnClickListener {

    val game = Game()
    val ai = AI()

    var isGameOver = false

    lateinit var map:MutableList<MutableList<AppCompatTextView>>
    lateinit var gridLayout: GridLayout
    lateinit var chooseContainer:LinearLayout

    lateinit var text_o:TextView
    lateinit var text_x:TextView

    var width = 100

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tic_tac_toe,container,false)
        calculateTextWidth()
        initViews(v)
        initClicks()
        newGame()
        return v
    }

    fun initViews(v:View)
    {
        gridLayout = v.findViewById(R.id.tic_tac_toe_map)

        text_o = v.findViewById(R.id.tic_tac_toe_o)
        text_x = v.findViewById(R.id.tic_tac_toe_x)

        chooseContainer = v.findViewById(R.id.tic_tac_toe_choose)

        addTextViewsToGrid()
    }

    fun initClicks()
    {
        text_o.setOnClickListener() {
            afterChooseSide(false)
        }
        text_x.setOnClickListener() {
            afterChooseSide(true)
        }
    }

    private fun calculateTextWidth()
    {
        val display = activity?.windowManager?.defaultDisplay
        width = (display?.width!! / 3) - 24
    }

    fun addTextViewsToGrid()
    {
        gridLayout.removeAllViews()
        gridLayout.columnCount = 3
        gridLayout.rowCount = 3

        map = MutableList(3, { MutableList<AppCompatTextView>(3, { AppCompatTextView(context) })})

        for (i in 0..2)
        {
            for (j in 0..2)
            {
                map[i][j].width = width
                map[i][j].height = width
                map[i][j].background = ContextCompat.getDrawable(context!!,R.drawable.border_order_number)
                map[i][j].gravity = Gravity.CENTER
                map[i][j].setPadding(16,16,16,16)
                map[i][j].setOnClickListener(this)
                map[i][j].tag = Point(i,j)

                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(map[i][j],18,70,8, TypedValue.COMPLEX_UNIT_SP)

                val params = GridLayout.LayoutParams()
                params.setMargins(10,10,10,10)

                gridLayout.addView(map[i][j],params)

            }
        }
    }

    fun chooseSide()
    {
        AnimationHelper.fadeIn(chooseContainer)
    }

    fun afterChooseSide(b:Boolean)
    {
        AnimationHelper.fadeOut(chooseContainer)
        game.isPlayerX = b
        ai.isPlayerX = !b
        AnimationHelper.fadeIn(gridLayout)
    }

    fun newGame()
    {
        isGameOver = false
        chooseSide()
        ai.init()
        game.newGame()
    }

    fun gameOver()
    {
        isGameOver = true
    }

    override fun onClick(v: View?) {
        if (!isGameOver) {
            val point = v?.tag as Point
            if (map[point.i][point.j].text.isEmpty()) {
                val select = if (game.isTurnX) 'X' else 'O'
                map[point.i][point.j].text = select.toString()
                game.map[point.i][point.j] = select
                if (game.check())
                {
                    Toast.makeText(context,"WWW",Toast.LENGTH_LONG).show()
                }
                game.isTurnX = !game.isTurnX
            }
        }
    }

    class Game
    {
        lateinit var map:MutableList<MutableList<Char>>

        var isTurnX:Boolean = true
        var isPlayerX = true

        companion object {
            val XXX = "XXX"
            val OOO = "OOO"
        }

        fun newGame()
        {
            map = MutableList(3, {MutableList(3,{' '})})
            isTurnX = true
        }

        fun gameOver()
        {

        }

        fun check() : Boolean
        {
            for (i in map)
            {
                var str = i.joinToString("")
                    if (str.equals(XXX) || str.equals(OOO))
                    {
                        return true
                    }
            }

            if (String(arrayOf(map[0][0],map[1][1],map[2][2]).toCharArray()).equals(XXX)
                    || String(arrayOf(map[0][0],map[1][1],map[2][2]).toCharArray()).equals(OOO))
                return true

            if (String(arrayOf(map[0][2],map[1][1],map[2][0]).toCharArray()).equals(XXX)
                    || String(arrayOf(map[0][2],map[1][1],map[2][0]).toCharArray()).equals(OOO))
                return true

            for (i in 0..2) {
                var str:String = ""

                for (j in 0..2)
                {
                    str += map[j][i]
                }

                if (str.equals(XXX) || str.equals(OOO))
                    return true
            }

            return false
        }

    }

    class AI
    {
        lateinit var map:MutableList<MutableList<Char>>

        var isPlayerX = false

        fun init()
        {
            map = MutableList(3, {MutableList(3,{' '})})
        }
    }

}