package com.example.instalock.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instalock.R
import com.merakianalytics.orianna.types.core.staticdata.ChampionSpell
import kotlinx.android.synthetic.main.item_ability.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class AbilityAdapter(private val listOfAbilities: ArrayList<ChampionSpell>): RecyclerView.Adapter<AbilityAdapter.ViewHolder>() {
    private val abilityKeys = listOf("Q", "W", "E", "Ultimate")

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(ability: ChampionSpell, currentKey: String) {
            GlobalScope.launch(Dispatchers.IO) {
                val iconUrl = ability.image.url
                val title = itemView.resources.getString(R.string.ability_full, currentKey , ability.name)
                val desc = ability.description
                launch(Dispatchers.Main) {
                    Glide.with(itemView.context).load(iconUrl).into(itemView.iv_ability_sprite)
                    itemView.tv_ability_title.text = title
                    itemView.tv_ability_desc.text = desc
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbilityAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ability, parent,false))
    }

    override fun getItemCount(): Int {
        return listOfAbilities.count()
    }

    override fun onBindViewHolder(holder: AbilityAdapter.ViewHolder, position: Int) {
        holder.bind(listOfAbilities[position], abilityKeys[position])
    }
}