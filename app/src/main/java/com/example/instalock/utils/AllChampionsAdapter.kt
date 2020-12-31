package com.example.instalock.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instalock.R
import com.merakianalytics.orianna.types.core.staticdata.Champion
import kotlinx.android.synthetic.main.item_champion.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AllChampionsAdapter(private val listOfChampions: ArrayList<Champion>, private val onClick: (championName: String) -> Unit): RecyclerView.Adapter<AllChampionsAdapter.ViewHolder>(), Filterable {

    private lateinit var finalList: ArrayList<Champion>
    var runOnce = true

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(champion: Champion) {
            GlobalScope.launch(Dispatchers.IO) {
                val iconUrl = champion.image.url
                val championName = champion.name
                launch(Dispatchers.Main) {
                    Glide.with(itemView.context).load(iconUrl).into(itemView.iv_champion_icon)
                    itemView.tv_champion_name.text = championName
                    itemView.setOnClickListener {
                        onClick(championName)
                    }
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
        if (listOfChampions.count() > 0 && runOnce) {
            finalList = ArrayList(listOfChampions)
            runOnce = false
        }
        holder.bind(listOfChampions[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList: ArrayList<Champion> = arrayListOf()
                val results = FilterResults()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(finalList)
                } else {
                    val pattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                    for (champion in finalList) {
                        GlobalScope.launch(Dispatchers.IO) {
                            if (champion.name.toLowerCase(Locale.ROOT).contains(pattern)) {
                                filteredList.add(champion)
                            }
                        }
                    }
                }
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listOfChampions.clear()
                listOfChampions.addAll(results?.values as List<Champion>)
                notifyDataSetChanged()
            }

        }
    }
}