package me.rankov.hth.country.list

import android.widget.ImageView
import me.rankov.hth.country.Country

class CountriesListPresenter(var countriesListView: CountriesListView?, val loadCountriesInteractor: CountriesListLoadInteractor) {

    fun onCreate() {
        countriesListView?.showProgress()
        loadCountriesInteractor.loadCountries(::onCountriesLoaded)
    }

    fun onCountriesLoaded(countries: List<Country>) {
        val countriesByPopulation = sortCountriesByPopulation(countries)
        countriesListView?.setCountries(countriesByPopulation)
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