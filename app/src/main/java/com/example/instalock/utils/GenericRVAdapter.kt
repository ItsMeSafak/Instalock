package com.example.instalock.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericRVAdapter<T> (private var list: ArrayList<T>): RecyclerView.Adapter<GenericRVAdapter<T>.ViewHolder>() {

    abstract fun bind(item: T, viewHolder: ViewHolder)

    inner class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericRVAdapter<T>.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent,false))
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: GenericRVAdapter<T>.ViewHolder, position: Int) {
        bind(list[position], holder)
    }
}