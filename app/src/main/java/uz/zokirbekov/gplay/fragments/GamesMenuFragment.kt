package uz.zokirbekov.gplay.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.zokirbekov.gplay.Games
import uz.zokirbekov.gplay.MainActivity
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.adapters.MainMenuAdapter
import uz.zokirbekov.gplay.fragments.gameFragments.*
import uz.zokirbekov.gplay.models.MainMenuModel

class GamesMenuFragment : BaseFragment(), MainMenuAdapter.OnGameClicked {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.fragment_games_menu, container, false)
        recyclerView = v.findViewById(R.id.list_games)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        initAdapter()
        return v;
    }

    override fun gameClicked(title: String) {
        when(title)
        {
            Games.ORDER_NUMBERS -> switchGame(OrderNumbersFragment())
            Games.CONNECT_FOUR -> switchGame(ConnectFourFragment())
            Games.TIC_TAC_TOE -> switchGame(TicTacToeFragment())
            Games.COLOR_STEP -> switchGame(ColorStepFragment())
            Games.FAST_TYPE -> switchGame(FastTypeFragment())
            Games.TAP_TAP_TAP -> switchGame(TapTapTapFragment())
        }
    }

    fun switchGame(fragment:Fragment)
    {
        (fragment as BaseFragment).parent = this
        (activity as MainActivity).switchFragment(fragment)
    }

    fun initAdapter()
    {
        recyclerView.adapter = MainMenuAdapter(context!!,getAllGames(),this)
    }

    fun getAllGames() : ArrayList<MainMenuModel>
    {
        var games = arrayListOf<MainMenuModel>(
                MainMenuModel(Games.ORDER_NUMBERS, R.drawable.order_numbers),
                MainMenuModel(Games.COLOR_STEP, R.drawable.color_step),
                MainMenuModel(Games.TIC_TAC_TOE, R.drawable.tic_tac_toe),
                MainMenuModel(Games.CONNECT_FOUR, R.drawable.connect_four),
                MainMenuModel(Games.FAST_TYPE, R.drawable.typewriter),
                MainMenuModel(Games.TAP_TAP_TAP,R.drawable.tap)
        )

        return games
    }

}