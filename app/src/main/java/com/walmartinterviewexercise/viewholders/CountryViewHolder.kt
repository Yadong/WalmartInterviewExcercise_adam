package com.walmartinterviewexercise.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.walmartinterviewexercise.R
import com.walmartinterviewexercise.databinding.CountryItemLayoutBinding
import com.walmartinterviewexercise.model.Country

class CountryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = CountryItemLayoutBinding.bind(view)

    fun bind(country: Country) {

        with(binding) {
            nameRegion.text =
                view.context.getString(R.string.name_region, country.name, country.region)
            code.text = country.code
            capital.text = country.capital
        }
    }
}
