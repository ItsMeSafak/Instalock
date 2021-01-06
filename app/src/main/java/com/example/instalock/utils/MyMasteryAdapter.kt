package com.example.instalock.utils

import com.bumptech.glide.Glide
import com.example.instalock.R
import com.example.instalock.models.MYMastery
import kotlinx.android.synthetic.main.item_card.view.*
import java.text.DecimalFormat

class MyMasteryAdapter(listOfChampions: ArrayList<MYMastery>) :
    GenericRVAdapter<MYMastery>(listOfChampions) {

    override fun bind(item: MYMastery?, viewHolder: ViewHolder) {
        item?.let {
            Glide.with(viewHolder.itemView.context).load(item.champion.imageURL)
                .into(viewHolder.itemView.iv_card_c_icon)
            viewHolder.itemView.tv_card_champion.text = item.champion.name
            viewHolder.itemView.tv_card_second.text =
                viewHolder.itemView.resources.getString(
                    R.string.champ_level,
                    item.level.toString()
                )
            viewHolder.itemView.tv_card_third.text =
                viewHolder.itemView.resources.getString(
                    R.string.champ_points,
                    DecimalFormat("###,###,###").format(item.points)
                )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_card
    }
}