package me.rankov.hth.country

import android.widget.ImageView

interface CountriesListView {
    fun hideProgress()
    fun showProgress()
    fun setCountries(countries: List<Country>)
    fun showMessage(message: String)
    fun navigateToCountry(country: Country, imageView: ImageView)
}