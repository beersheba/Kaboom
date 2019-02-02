package me.rankov.hth.view

import android.widget.ImageView
import me.rankov.hth.model.Country

interface CountriesListView {
    fun hideProgress()
    fun showProgress()
    fun setCountries(countries: List<Country>)
    fun showMessage(message: String)
    fun navigateToCountry(country: Country, imageView: ImageView)
}