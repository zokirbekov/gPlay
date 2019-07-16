package uz.zokirbekov.gplay.fragments.gameFragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.graphics.ColorUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.fragments.BaseGameFragment
import java.util.*
import kotlin.collections.ArrayList

class ColorStepFragment : BaseGameFragment(), View.OnClickListener {

    lateinit var blueImage : ImageView
    lateinit var redImage : ImageView
    lateinit var greenImage : ImageView
    lateinit var yellowImage : ImageView

    lateinit var game:ColorStep
    var handler:Handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_color_step, container, false)

        initViews(v)
        initClicks()

        return v
    }

    fun initViews(v:View)
    {
        blueImage = v.findViewById(R.id.connect_four_blue)
        redImage = v.findViewById(R.id.connect_four_red)
        greenImage = v.findViewById(R.id.connect_four_green)
        yellowImage = v.findViewById(R.id.connect_four_yellow)
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
        game = ColorStep()
    }

    fun changeColor(color:Int) : Int
    {
        var hls:FloatArray = FloatArray(3)
        ColorUtils.RGBToHSL(Color.red(color),Color.green(color),Color.blue(color),hls)
        hls[2] -= 0.2f
        return ColorUtils.HSLToColor(hls)
    }

    override fun onClick(v: View?) {

        val imageColor = v as ImageView

        var color = imageColor.imageTintList.defaultColor
        color = changeColor(color)
        //imageColor.imageTintList =

        when(imageColor.id)
        {
            R.id.connect_four_blue -> println()
            R.id.connect_four_red -> println()
            R.id.connect_four_green -> println()
            R.id.connect_four_yellow -> println()
        }
    }

    class ColorStep()
    {
        var step:ArrayList<Int> = ArrayList<Int>()

        var score = 1
        var currentPosition = 0

        fun nextStep()
        {
            val num = Random().nextInt(4)
            step.add(num)
            currentPosition++
            if (isNextLevel())
            {
                currentPosition = 0
                score++
            }

        }

        fun isNextLevel() : Boolean
        {
            return currentPosition == score
        }

        fun checkStep(color:Int) : Boolean
        {
            return step[currentPosition] == color
        }

    }

}