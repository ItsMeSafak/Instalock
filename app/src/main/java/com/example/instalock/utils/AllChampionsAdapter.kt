package com.example.instalock.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instalock.R
import com.merakianalytics.orianna.types.core.staticdata.Champion
import kotlinx.android.synthetic.main.item_champion.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AllChampionsAdapter(private val listOfChampions: ArrayList<Champion>): RecyclerView.Adapter<AllChampionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(champion: Champion) {
            GlobalScope.launch(Dispatchers.IO) {
                val iconUrl = champion.image.url
                val championName = champion.name
                launch(Dispatchers.Main) {
                    Glide.with(itemView.context).load(iconUrl).into(itemView.iv_champion_icon)
                    itemView.tv_champion_name.text = championName
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllChampionsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_champion, parent,false))
    }

    override fun getItemCount(): Int {
        return listOfChampions.count()
    }

    override fun onBindViewHolder(holder: AllChampionsAdapter.ViewHolder, position: Int) {
        holder.bind(listOfChampions[position])
    }
}