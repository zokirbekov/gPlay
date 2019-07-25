package uz.zokirbekov.gplay.fragments.gameFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.fragments.BaseGameFragment

class TapTapTapFragment : BaseGameFragment() {

    lateinit var map:RelativeLayout

    val game = Game()
    var isGameOver = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tap_tap_tap, container, false)
        initViews(v)
        return v
    }

    private fun initViews(v:View)
    {
        map = v.findViewById(R.id.tap_tap_tap_map)
    }

    private fun newGame()
    {
        isGameOver = false
        game.newGame()
    }

    private fun gameOver()
    {
        isGameOver = true
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