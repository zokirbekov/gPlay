package uz.zokirbekov.gplay.fragments.gameFragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.graphics.ColorUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.fragments.BaseGameFragment
import uz.zokirbekov.gplay.utils.AnimationHelper
import java.util.*
import kotlin.collections.ArrayList

class ColorStepFragment : BaseGameFragment(), View.OnClickListener {

    lateinit var blueImage : ImageView
    lateinit var redImage : ImageView
    lateinit var greenImage : ImageView
    lateinit var yellowImage : ImageView

    lateinit var textScore : TextView

    lateinit var newGame: Button

    var game:ColorStep? = null
    var handler:Handler = Handler()

    companion object {
        val BLUE = 1
        val RED = 2
        val GREEN = 3
        val YELLOW = 4
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_color_step, container, false)

        initViews(v)

        return v
    }

    fun initViews(v:View)
    {
        blueImage = v.findViewById(R.id.connect_four_blue)
        redImage = v.findViewById(R.id.connect_four_red)
        greenImage = v.findViewById(R.id.connect_four_green)
        yellowImage = v.findViewById(R.id.connect_four_yellow)
        newGame = v.findViewById(R.id.button_new_game)
        textScore = v.findViewById(R.id.text_score)

        newGame.setOnClickListener { newGame() }

        blueImage.tag = BLUE
        redImage.tag = RED
        greenImage.tag = GREEN
        yellowImage.tag = YELLOW
    }

    fun detachClicks()
    {
        blueImage.setOnClickListener(null)
        redImage.setOnClickListener(null)
        greenImage.setOnClickListener(null)
        yellowImage.setOnClickListener(null)
    }

    fun initClicks()
    {
        blueImage.setOnClickListener(this)
        redImage.setOnClickListener(this)
        greenImage.setOnClickListener(this)
        yellowImage.setOnClickListener(this)
    }

    fun newGame()
    {
        if (game == null)
            game = ColorStep()

        game?.newGame()
        game?.gameListener = object : GameListener
        {
            override fun nextLevel(score: Int) {
                textScore.text = "Score : " + score
            }

        }
        AnimationHelper.fadeOut(newGame)
        initClicks()
        nextStep(1L)
    }

    fun turnOffAndturnOnImageView(imageView: ImageView?,time:Long)
    {
        handler.postDelayed({
            changeColor(imageView,-0.2f)
            handler.postDelayed({changeColor(imageView,0.2f)},300)
        },300*time + 300*(time-1))

    }

    fun changeColor(imageView: ImageView?,changeS:Float)
    {
        var color = imageView?.backgroundTintList?.defaultColor
        val hls:FloatArray = FloatArray(3)
        ColorUtils.RGBToHSL(Color.red(color!!),Color.green(color),Color.blue(color),hls)
        hls[2] += changeS
        color = ColorUtils.HSLToColor(hls)
        imageView?.backgroundTintList = ColorStateList.valueOf(color)
    }

    fun turnOffAndturnOnImageViewByTag(color:Int,time:Long)
    {
        var imageView:ImageView? = null
        when(color)
        {
            BLUE   -> imageView = blueImage
            RED    -> imageView = redImage
            GREEN  -> imageView = greenImage
            YELLOW -> imageView = yellowImage
        }
        turnOffAndturnOnImageView(imageView,time)
    }

    fun nextLevel()
    {
        showSteps()
        nextStep((game?.step?.size?.plus(1))!!.toLong() )
    }


    fun nextStep(time:Long)
    {
        val color = game?.nextStep()
        turnOffAndturnOnImageViewByTag(color!!,time)
    }

    fun showSteps()
    {
        var time = 1L
        for (i in game?.step!!)
        {
            turnOffAndturnOnImageViewByTag(i,time)
            time++
        }
    }


    override fun onClick(v: View?) {

        val imageColor = v as ImageView

        turnOffAndturnOnImageView(imageColor,0)

        if (!(game?.checkStep(imageColor.tag as Int)!!))
        {
            Toast.makeText(context,"Game over", Toast.LENGTH_LONG).show();
            handler.postDelayed( {showSteps()} , 1000)
            detachClicks()
            AnimationHelper.fadeIn(newGame)
        }
        else
        {
            if (game?.isNextLevel()!!)
            {
                game?.nextLevel()
                handler.postDelayed({nextLevel()},1000)

            }
            else
            {
                //nextStep()
            }
        }


    }



    class ColorStep()
    {
        var step:ArrayList<Int> = ArrayList<Int>()

        var gameListener:GameListener? = null

        var score = 1
        var currentPosition = 0

        fun nextStep() : Int
        {
            val num = Random().nextInt(4) + 1
            step.add(num)
            return num
        }

        fun nextLevel()
        {
            score++
            currentPosition = 0
            gameListener?.nextLevel(score)
        }

        fun newGame()
        {
            score = 1
            currentPosition = 0
            step.clear()
        }

        fun isNextLevel() : Boolean
        {
            return currentPosition == score
        }

        fun checkStep(color:Int) : Boolean
        {
            return step[currentPosition++] == color
        }

    }

    interface GameListener
    {
        fun nextLevel(score:Int)
    }

}