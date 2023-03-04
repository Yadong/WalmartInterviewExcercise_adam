package com.walmartinterviewexercise.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.walmartinterviewexercise.R
import com.walmartinterviewexercise.model.Country
import com.walmartinterviewexercise.viewholders.CountryViewHolder

class HomeAdapter(val mutableList: MutableList<Country> = mutableListOf<Country>()) :
    RecyclerView.Adapter<CountryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.country_item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = mutableList.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(mutableList[position])
    }
}
