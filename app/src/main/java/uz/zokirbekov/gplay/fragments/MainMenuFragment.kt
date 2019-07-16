package uz.zokirbekov.gplay.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.zokirbekov.gplay.MainActivity
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.ui.ImageWithTextButton

class MainMenuFragment : Fragment(), View.OnClickListener {

    lateinit var games:ImageWithTextButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_main_menu, container, false)

        initViews(v)
        initClicks()

        return v
    }

    fun initViews(v:View)
    {
        games = v.findViewById(R.id.games)
    }

    fun initClicks()
    {
        games.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.games -> (activity as MainActivity).switchFragment(GamesMenuFragment())
        }
    }

}