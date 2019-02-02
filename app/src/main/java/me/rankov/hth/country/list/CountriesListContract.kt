package me.rankov.hth.country.list

import android.widget.ImageView
import me.rankov.hth.country.Country

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