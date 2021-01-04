package com.example.instalock.utils

import com.bumptech.glide.Glide
import com.example.instalock.R
import com.merakianalytics.orianna.types.core.match.Match
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyMatchHistoryAdapter(matches: ArrayList<Match>) : GenericRVAdapter<Match>(matches) {

    override fun bind(item: Match?, viewHolder: ViewHolder) {
        GlobalScope.launch(Dispatchers.IO) {
            item?.let {
                val participantMe = item.participants[0]
                val imageUrl = participantMe.champion.skins[0].splashImageURL
                val championName = participantMe.champion.name

                val kills = participantMe.stats.kills.toString()
                val deaths = participantMe.stats.deaths.toString()
                val assists = participantMe.stats.assists.toString()

                launch(Dispatchers.Main) {
                    Glide.with(viewHolder.itemView.context).load(imageUrl)
                        .into(viewHolder.itemView.iv_card_c_icon)
                    viewHolder.itemView.tv_card_champion.text = championName
                    viewHolder.itemView.tv_card_second.text =
                        viewHolder.itemView.resources.getString(
                            R.string.kda,
                            kills, deaths, assists
                        )
                    viewHolder.itemView.tv_card_third.text = it.coreData.mode.toString()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_card
    }
}