package me.rankov.hth.country

import android.widget.ImageView

class CountriesListPresenter(var countriesListView: CountriesListView?, val loadCountriesInteractor: CountriesListLoadInteractor) {

    fun onCreate() {
        countriesListView?.showProgress()
        loadCountriesInteractor.loadCountries(::onCountriesLoaded)
    }

    fun onCountriesLoaded(countries: List<Country>) {
        countriesListView?.setCountries(sortCountriesByPopulation(countries))
        countriesListView?.hideProgress()
    }

    private fun sortCountriesByPopulation(countries: List<Country>): List<Country> {
        return countries.sortedWith(compareByDescending {it.population})
    }

    fun onCountryClicked(country: Country, imageView: ImageView) {
        countriesListView?.navigateToCountry(country, imageView)
    }

    fun onDestroy() {
        countriesListView = null
    }
}