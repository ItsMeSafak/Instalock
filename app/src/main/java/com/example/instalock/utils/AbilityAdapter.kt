package com.example.instalock.utils

import com.bumptech.glide.Glide
import com.example.instalock.R
import com.merakianalytics.orianna.types.core.staticdata.ChampionSpell
import kotlinx.android.synthetic.main.item_ability.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class AbilityAdapter(listOfAbilities: ArrayList<ChampionSpell>): GenericRVAdapter<ChampionSpell>(listOfAbilities) {
    private val abilityKeys = listOf("Q", "W", "E", "Ultimate")

    override fun bind(item: ChampionSpell?, viewHolder: GenericRVAdapter<ChampionSpell>.ViewHolder) {
        GlobalScope.launch(Dispatchers.IO) {
            item?.let {
                val iconUrl = item.image?.url
                val title = viewHolder.itemView.resources.getString(R.string.ability_full, abilityKeys[viewHolder.adapterPosition], item.name)
                val desc = item.description
                launch(Dispatchers.Main) {
                    Glide.with(viewHolder.itemView.context).load(iconUrl).into(viewHolder.itemView.iv_ability_sprite)
                    viewHolder.itemView.tv_ability_title.text = title
                    viewHolder.itemView.tv_ability_desc.text = desc
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_ability
    }
}