package me.rankov.hth.country.list

import android.widget.ImageView
import me.rankov.hth.country.Country

interface CountriesListPresenter {
    fun onCreate()
    fun onCountriesLoaded(countries: List<Country>)
    fun onCountryClicked(country: Country, imageView: ImageView)
    fun onDestroy()
}