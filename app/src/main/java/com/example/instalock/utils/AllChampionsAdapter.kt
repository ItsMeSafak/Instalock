package com.example.instalock.utils

import android.widget.Filter
import android.widget.Filterable
import com.bumptech.glide.Glide
import com.example.instalock.R
import com.example.instalock.exceptions.FilterError
import com.merakianalytics.orianna.types.core.staticdata.Champion
import kotlinx.android.synthetic.main.item_champion.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class AllChampionsAdapter(private val listOfChampions: ArrayList<Champion>, private val onClick: (championName: String) -> Unit): GenericRVAdapter<Champion>(listOfChampions), Filterable {

    private lateinit var finalList: ArrayList<Champion>
    var runOnce = true

    override fun bind(item: Champion, viewHolder: GenericRVAdapter<Champion>.ViewHolder) {
        GlobalScope.launch(Dispatchers.IO) {
            if (listOfChampions.count() > 0 && runOnce) {
                finalList = ArrayList(listOfChampions)
                runOnce = false
            }

            val iconUrl = item.image.url
            val championName = item.name
            launch(Dispatchers.Main) {
                Glide.with(viewHolder.itemView.context).load(iconUrl).into(viewHolder.itemView.iv_champion_icon)
                viewHolder.itemView.tv_champion_name.text = championName
                viewHolder.itemView.setOnClickListener {
                    onClick(championName)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_champion
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
                try {
                    listOfChampions.clear()
                    listOfChampions.addAll(results?.values as List<Champion>)
                    notifyDataSetChanged()
                } catch (ex: Exception) {
                    throw FilterError("Oops! Something went wrong filtering the data...")
                }
            }
        }
    }
}