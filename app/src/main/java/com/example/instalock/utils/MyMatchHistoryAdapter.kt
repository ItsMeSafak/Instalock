package com.example.instalock.utils

import com.bumptech.glide.Glide
import com.example.instalock.R
import com.example.instalock.models.MYMatch
import kotlinx.android.synthetic.main.item_card.view.*

class MyMatchHistoryAdapter(matches: ArrayList<MYMatch>) : GenericRVAdapter<MYMatch>(matches) {

    override fun bind(item: MYMatch?, viewHolder: ViewHolder) {
        item?.let {
            Glide.with(viewHolder.itemView.context).load(item.championPlayed.imageURL)
                .into(viewHolder.itemView.iv_card_c_icon)
            viewHolder.itemView.tv_card_champion.text = item.championPlayed.name
            viewHolder.itemView.tv_card_second.text =
                viewHolder.itemView.resources.getString(
                    R.string.kda,
                    item.kills.toString(),
                    item.deaths.toString(),
                    item.assists.toString()
                )
            viewHolder.itemView.tv_card_third.text = item.mode
            viewHolder.itemView.tv_card_win.apply {
                text = if (item.isWinner) "VICTORY" else "DEFEAT"
                setTextColor(
                    if (item.isWinner) viewHolder.itemView.resources.getColor(R.color.colorGreen)
                    else viewHolder.itemView.resources.getColor(R.color.colorRed)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_card
    }
}