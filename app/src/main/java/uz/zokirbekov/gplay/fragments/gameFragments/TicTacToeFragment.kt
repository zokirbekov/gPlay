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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.fragments.BaseGameFragment
import uz.zokirbekov.gplay.utils.AnimationHelper
import uz.zokirbekov.gplay.utils.Drawer
import uz.zokirbekov.gplay.utils.Point

class TicTacToeFragment : BaseGameFragment(), View.OnClickListener {

    val game = Game()
    val ai = AI()

    var isGameOver = false

    lateinit var map:MutableList<MutableList<AppCompatTextView>>
    lateinit var gridLayout: GridLayout
    lateinit var chooseContainer:LinearLayout

    lateinit var drawImage: ImageView

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

        drawImage = v.findViewById(R.id.drawImage)

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

    fun drawLine(positions:MutableList<Point>)
    {
        val st = map[positions[0].i][positions[0].j]
        val et = map[positions[1].i][positions[1].j]

        val startX:Float = st.x + st.width/2 - 20
        val startY:Float = st.y + st.height/2 - 20

        val endX:Float = et.x + et.width/2 - 20
        val endY:Float = et.y + et.height/2 - 10 + 20

        //drawImage.setImageBitmap(Drawer.drawLine(drawImage,startX,startY,endX,endY))
        AnimationHelper.animateDrawLine(drawImage,startX,startY,endX,endY)
    }

    override fun onClick(v: View?) {
        if (!isGameOver) {
            val point = v?.tag as Point
            if (map[point.i][point.j].text.isEmpty()) {
                val select = if (game.isTurnX) 'X' else 'O'
                map[point.i][point.j].text = select.toString()
                game.map[point.i][point.j] = select
                val positions = MutableList<Point>(2, {Point(0,0)})
                if (game.check(positions))
                {
                    gameOver()
                    drawLine(positions)
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

        fun check(outPosition:MutableList<Point>) : Boolean
        {
            for (i in map)
            {
                var str = i.joinToString("")
                    if (str.equals(XXX) || str.equals(OOO))
                    {
                        outPosition[0].i = map.indexOf(i)
                        outPosition[0].j = 0
                        outPosition[1].i = outPosition[0].i
                        outPosition[1].j = 0
                        return true
                    }
            }

            if (String(arrayOf(map[0][0],map[1][1],map[2][2]).toCharArray()).equals(XXX)
                    || String(arrayOf(map[0][0],map[1][1],map[2][2]).toCharArray()).equals(OOO)) {
                outPosition[0].i = 0
                outPosition[0].j = 0
                outPosition[1].i = 2
                outPosition[1].j = 2
                return true
            }

            if (String(arrayOf(map[0][2],map[1][1],map[2][0]).toCharArray()).equals(XXX)
                    || String(arrayOf(map[0][2],map[1][1],map[2][0]).toCharArray()).equals(OOO)) {

                outPosition[0].i = 0
                outPosition[0].j = 2
                outPosition[1].i = 2
                outPosition[1].j = 0

                return true
            }

            for (i in 0..2) {
                var str:String = ""

                for (j in 0..2)
                {
                    str += map[j][i]
                }

                if (str.equals(XXX) || str.equals(OOO)) {

                    outPosition[0].i = 0
                    outPosition[0].j = i
                    outPosition[1].i = 2
                    outPosition[1].j = i

                    return true
                }
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