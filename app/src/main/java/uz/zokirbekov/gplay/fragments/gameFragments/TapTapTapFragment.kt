package uz.zokirbekov.gplay.fragments.gameFragments

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.fragments.BaseGameFragment
import uz.zokirbekov.gplay.utils.AnimationHelper
import java.util.*
import kotlin.collections.ArrayList

class TapTapTapFragment : BaseGameFragment(), View.OnClickListener {

    lateinit var map:RelativeLayout
    lateinit var button:Button

    var height:Int = 500
    var width:Int = 500
    val TIME_CREATE = 2000L
    val TIME_DOWN_ANIMATION = 10000L
    val game = Game()

    var isGameOver = true
    var isCanceled = true

    val handler = Handler()

    val NEW_GAME = "NEW GAME"
    var currentPosNewGame = 1

    val buttons = ArrayList<Button>()

    val action = object : Runnable
    {
        override fun run() {
            addView()
            handler.postDelayed(this, TIME_CREATE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tap_tap_tap, container, false)
        initViews(v)
        initClicks()
        height = activity?.windowManager?.defaultDisplay?.height!!
        width = activity?.windowManager?.defaultDisplay?.width!!
        return v
    }

    private fun initViews(v:View)
    {
        map = v.findViewById(R.id.tap_tap_tap_map)
        button = v.findViewById(R.id.tap_tap_tap_new_game)
    }

    private fun initClicks()
    {
        button.setOnClickListener {
            if (currentPosNewGame <= NEW_GAME.length) {
                button.setText(NEW_GAME.substring(0, currentPosNewGame))
                currentPosNewGame++
            }
            else
            {
                AnimationHelper.fadeOut(button)
                newGame()
            }
        }
    }

    private fun addView()
    {
        val button = Button(context)
        button.width = 70
        button.height = 70

        val random = Random()
        val number = random.nextInt(game.score + 1) + 10
        val x = random.nextInt(width - 100) + 50

        var params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT)

        params.leftMargin = x
        params.topMargin = 10

        button.layoutParams = params

        button.setText(number.toString())
        button.setOnClickListener(this)
        map.addView(button)
        buttons.add(button)
        down(button)
    }

    private fun down(v:View)
    {
        isCanceled = isGameOver;
        val random = Random()
        val anim = ObjectAnimator.ofFloat(v,View.TRANSLATION_Y, height.toFloat())
        anim.duration = TIME_DOWN_ANIMATION + random.nextInt(2000)
        anim.addListener(object : Animator.AnimatorListener
        {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                if (!isCanceled) {
                    if (!isGameOver)
                        gameOver()
                }
            }

        })
        v.tag = anim
        anim.start()

    }

    private fun disableAllAnimations()
    {
        val iterate = buttons.listIterator()
        while (iterate.hasNext()) {
            val oldValue = iterate.next()
            val anim = oldValue.tag as ObjectAnimator
            anim.cancel()
        }
    }

    private fun startTimer()
    {
        action.run()
    }

    private fun stopTimer()
    {
        handler.removeCallbacks(action)
    }

    private fun newGame()
    {
        disableAllAnimations()
        isGameOver = false
        isCanceled = false

        map.removeAllViews()
        buttons.clear()
        game.newGame()
        startTimer()
    }

    private fun gameOver()
    {
        isGameOver = true
        stopTimer()
    }

    override fun onClick(v: View?) {
        val button = v as Button
        var number = button.text.toString().toInt()
        number--
        if (number == 0)
        {
            (button.tag as ObjectAnimator).cancel()
            buttons.remove(button)
            map.removeView(button)
            game.score++
            isCanceled = true
        }
        else
        {
            button.setText(number.toString())
        }
    }

    class Game()
    {

        var score = 0

        fun newGame()
        {
            score = 0
        }

    }

}