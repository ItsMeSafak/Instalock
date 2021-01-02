package com.example.instalock.utils

import com.bumptech.glide.Glide
import com.example.instalock.R
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyMasteryAdapter(listOfChampions: ArrayList<ChampionMastery>) :
    GenericRVAdapter<ChampionMastery>(listOfChampions) {

    override fun bind(item: ChampionMastery?, viewHolder: ViewHolder) {
        GlobalScope.launch(Dispatchers.IO) {
            item?.let {
                val imageUrl = item.champion.skins[0].splashImageURL
                val championName = item.champion.name
                val level = item.level
                val points = item.points
                launch(Dispatchers.Main) {
                    Glide.with(viewHolder.itemView.context).load(imageUrl)
                        .into(viewHolder.itemView.iv_card_c_icon)
                    viewHolder.itemView.tv_card_champion.text = championName
                    viewHolder.itemView.tv_card_second.text =
                        viewHolder.itemView.resources.getString(
                            R.string.champ_level,
                            level.toString()
                        )
                    viewHolder.itemView.tv_card_third.text =
                        viewHolder.itemView.resources.getString(
                            R.string.champ_points,
                            points.toString()
                        )
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_card
    }
}