package me.rankov.hth.country

import android.widget.ImageView

class CountriesListPresenter(var countriesListView: CountriesListView?, val loadCountriesInteractor: CountriesListLoadInteractor) {

    fun onCreate() {
        countriesListView?.showProgress()
        loadCountriesInteractor.loadCountries(::onCountriesLoaded)
    }

    fun onCountriesLoaded(countries: List<Country>) {
        countriesListView?.setCountries(countries)
        countriesListView?.hideProgress()
    }

    fun onCountryClicked(country: Country, imageView: ImageView) {
        countriesListView?.navigateToCountry(country, imageView)
    }

    fun onDestroy() {
        countriesListView = null
    }
}