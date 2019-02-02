package me.rankov.hth.presenter

import android.widget.ImageView
import me.rankov.hth.model.Country

interface CountriesListPresenter {
    fun onCreate()
    fun onCountriesLoaded(countries: List<Country>)
    fun onCountryClicked(country: Country, imageView: ImageView)
    fun onDestroy()
}