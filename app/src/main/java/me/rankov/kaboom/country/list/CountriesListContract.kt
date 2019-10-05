package me.rankov.kaboom.country.list

import android.widget.ImageView
import me.rankov.kaboom.BasePresenter
import me.rankov.kaboom.BaseView
import me.rankov.kaboom.country.Country

interface CountriesListContract {
    interface View: BaseView {
        fun hideProgress()
        fun showProgress()
        fun setCountries(countries: List<Country>)
        fun showMessage(message: String)
        fun navigateToCountry(country: Country, imageView: ImageView)
    }

    interface Presenter: BasePresenter {
        fun onCountriesLoaded(countries: List<Country>)
        fun onCountryClicked(country: Country, imageView: ImageView)
    }
}