package com.example.countryinfo.presentation

import android.view.View
import com.example.countryinfo.AdapterItem
import com.example.countryinfo.R
import com.example.countryinfo.databinding.CountryItemBinding
import com.example.domain.models.Country

data class CountryItemHelper(
    val country: Country,
    val onClickAction: (Country) -> Unit
) : AdapterItem() {
    override val content: Country
        get() = country

    override val id: String
        get() = country.code + country.capital

    override val layoutResource: Int
        get() = R.layout.country_item

    override fun bind(view: View) {
        CountryItemBinding.bind(view).apply {
            root.setOnClickListener { onClickAction(country) }
            tvNameRegion.text = String.format("%s, %s", country.name, country.region)
            tvCountryCode.text = country.code
            tvCapitalCity.text = country.capital
        }
    }
}
