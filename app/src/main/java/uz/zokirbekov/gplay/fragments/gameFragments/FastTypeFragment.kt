package uz.zokirbekov.gplay.fragments.gameFragments

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.AppCompatEditText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.fragments.BaseGameFragment
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList
import android.util.DisplayMetrics
import android.widget.RelativeLayout
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.ResultReceiver
import android.support.v4.content.ContextCompat.getSystemService
import android.view.inputmethod.InputMethodManager


class FastTypeFragment : BaseGameFragment() {

    lateinit var editText:AppCompatEditText
    lateinit var fire:ImageView
    lateinit var map:RelativeLayout

    val texts:ArrayList<TextView> = ArrayList()
    val handler = Handler()
    val game = Game()

    val action = object : Runnable
    {
        override fun run() {
            addText(game.addWord())
            handler.postDelayed(this,3000)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_fast_type, container, false)
        initViews(v)
        initClicks()
        startViewAnimations()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?

        imm!!.showSoftInput(editText,
                0)
        return v
    }

    private fun initViews(v:View)
    {
        editText = v.findViewById(R.id.fast_type_text)
        fire = v.findViewById(R.id.fast_type_fire)
        map = v.findViewById(R.id.fast_type_map)
    }

    private fun initClicks()
    {
        fire.setOnClickListener {

            val word = editText.text?.trim().toString()
            if (word.toLowerCase().equals("new game"))
            {
                newGame()
            }
            else
                checkWord(word)

        }
    }

    private fun startViewAnimations()
    {
        val anim = AnimationUtils.loadAnimation(context,R.anim.scale_animation)
        anim.repeatCount = Animation.INFINITE
        fire.startAnimation(anim)
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
        game.newGame()
        map.removeAllViews()
        startTimer()
    }

    private fun getRandomColor() : Int
    {
        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        return color
    }

    private fun checkWord(word:String)
    {
        if (game.checkWord(word))
        {
            var needText:TextView? = null
            for (i in texts)
            {
                if (i.text.equals(word)) {
                    needText = i
                    break
                }
            }
            map.removeView(needText)
            texts.remove(needText)
            editText.setText("")
        }
    }

    private fun down(v:View)
    {
        var intA:IntArray = IntArray(2)
        editText.getLocationInWindow(intA)
        val y = intA[1]
        val anim = ObjectAnimator.ofFloat(v,View.TRANSLATION_Y, y.toFloat())
        anim.duration = 5000
        anim.start()
    }

    private fun addText(word:String)
    {
        val random = Random()

        val textView:TextView = TextView(context)
        textView.text = word
        textView.textSize = 22f
        textView.setTextColor(getRandomColor())

        val width = map.width - 200
        var x = random.nextInt(width) + 100

        var params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT)

//        val displayMetrics = context?.getResources()?.displayMetrics!!
//        x = Math.round(x * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

        params.leftMargin = x
        params.topMargin = 10

        textView.layoutParams = params

        down(textView)

        texts.add(textView)

        map.addView(textView)
    }

    class Game()
    {

        val words = ArrayList<String>()
        var score = 0

        fun newGame()
        {
            words.clear()
            score = 0
        }

        fun checkWord(word:String) : Boolean
        {
            if (word in words)
            {
                words.remove(word)
                return true
            }
            return false
        }

        fun addWord() : String
        {
            if (words.size != Words.listOfWords.size) {
                val random = Random()
                var word: String
                do {
                    val i = random.nextInt(Words.listOfWords.size)
                    word = Words.listOfWords[i]
                } while (word in words)
                words.add(word)
                return word
            }
            return ""
        }

    }

    class Words
    {
        companion object {
            val listOfWords = arrayOf(
                    "Fast Type",
                    "Zokirbekov",
                    "gPlay",
                    "Fast fAst, fasT",
                    "Why so serious ?",
                    "Just do It",
                    "trololol",
                    "I see you",
                    "You are lucky",
                    "Maybe slowly ?",
                    "Am I stupid ?",
                    "Let's :)",
                    "Wakanda forever",
                    "Just enough"
            )
        }
    }

}