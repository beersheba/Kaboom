package me.rankov.kaboom.country.list

import android.widget.ImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.rankov.kaboom.model.ActionItem
import me.rankov.kaboom.model.Country
import me.rankov.kaboom.country.details.ActionItems
import me.rankov.kaboom.country.list.CountriesListContract.Presenter
import me.rankov.kaboom.country.list.CountriesListContract.View

class CountriesListPresenterImpl(var countriesListView: View?,
                                 val loadCountriesInteractor: CountriesListLoadInteractor) : Presenter {

    override fun onCreate() {
        countriesListView?.setBackground()
        countriesListView?.showProgress()
        loadCountriesInteractor.loadActionItems(::onActionItemsLoaded)
        loadCountriesInteractor.loadCountries(::onCountriesLoaded)
        GlobalScope.launch {
            loadCountriesInteractor.logAmazonTokens()
        }
    }

    private fun onActionItemsLoaded(list: List<ActionItem>) {
        ActionItems.init(list)
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

    override fun onSignOut() {
    }

    override fun detachView() {
        countriesListView = null
    }
}