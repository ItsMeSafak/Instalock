package com.example.instalock.utils

import com.bumptech.glide.Glide
import com.example.instalock.R
import com.merakianalytics.orianna.types.core.spectator.Player
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamAdapter(listOfChampions: ArrayList<Player>) :
    GenericRVAdapter<Player>(listOfChampions) {

    override fun bind(item: Player?, viewHolder: ViewHolder) {
        GlobalScope.launch(Dispatchers.IO) {
            item?.let {
                val imageUrl = item.champion.skins[0].splashImageURL
                val summonerName = item.summoner.name

                launch(Dispatchers.Main) {
                    Glide.with(viewHolder.itemView.context).load(imageUrl)
                        .into(viewHolder.itemView.iv_card_c_icon)
                    viewHolder.itemView.tv_card_champion.text = summonerName
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_card
    }
}