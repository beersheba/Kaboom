package me.rankov.kaboom.country.list

import android.widget.ImageView
import me.rankov.kaboom.country.Country

interface CountriesListContract {
    interface View {
        fun hideProgress()
        fun showProgress()
        fun setCountries(countries: List<Country>)
        fun showMessage(message: String)
        fun navigateToCountry(country: Country, imageView: ImageView)
    }

    interface Presenter {
        fun onCreate()
        fun onCountriesLoaded(countries: List<Country>)
        fun onCountryClicked(country: Country, imageView: ImageView)
        fun onDestroy()
    }
}