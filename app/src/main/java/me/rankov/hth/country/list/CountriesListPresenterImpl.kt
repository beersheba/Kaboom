package me.rankov.hth.country.list

import android.widget.ImageView
import me.rankov.hth.country.Country

class CountriesListPresenterImpl(var countriesListView: CountriesListView?,
                                 val loadCountriesInteractor: CountriesListLoadInteractor) : CountriesListPresenter {

    override fun onCreate() {
        countriesListView?.showProgress()
        loadCountriesInteractor.loadCountries(::onCountriesLoaded)
    }

    override fun onCountriesLoaded(countries: List<Country>) {
        val countriesByPopulation = sortCountriesByPopulation(countries)
        countriesListView?.setCountries(countriesByPopulation)
        countriesListView?.hideProgress()
    }

    private fun sortCountriesByPopulation(countries: List<Country>): List<Country> {
        return countries.sortedWith(compareByDescending { it.population })
    }

    override fun onCountryClicked(country: Country, imageView: ImageView) {
        countriesListView?.navigateToCountry(country, imageView)
    }

    override fun onDestroy() {
        countriesListView = null
    }
}