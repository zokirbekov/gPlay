package uz.zokirbekov.gplay.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import uz.zokirbekov.gplay.R
import uz.zokirbekov.gplay.models.MainMenuModel

class MainMenuAdapter(val context:Context, var games:ArrayList<MainMenuModel>, var listener:OnGameClicked) : RecyclerView.Adapter<MainMenuAdapter.VH>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VH {
        return VH(LayoutInflater.from(context).inflate(R.layout.item_games_menu, p0, false))
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(p0: VH, p1: Int) {

        p0.image.setImageResource(games[p1].image)
        p0.title.text = games[p1].title
        p0.itemView.setOnClickListener { listener.gameClicked(games[p1].title) }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image: ImageView = itemView.findViewById(R.id.menu_image)
        val title: TextView = itemView.findViewById(R.id.menu_title)
        init {

        }
    }

    interface OnGameClicked
    {
        fun gameClicked(title:String)
    }
}

