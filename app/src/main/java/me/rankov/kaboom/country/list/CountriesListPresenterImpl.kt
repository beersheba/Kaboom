package me.rankov.kaboom.country.list

import android.widget.ImageView
import me.rankov.kaboom.country.Country
import me.rankov.kaboom.country.Weapon
import me.rankov.kaboom.country.details.Weapons
import me.rankov.kaboom.country.list.CountriesListContract.Presenter
import me.rankov.kaboom.country.list.CountriesListContract.View

class CountriesListPresenterImpl(var countriesListView: View?,
                                 val loadCountriesInteractor: CountriesListLoadInteractor) : Presenter {

    override fun onCreate() {
        countriesListView?.setBackground()
        countriesListView?.showProgress()
        loadCountriesInteractor.loadWeapons(::onWeaponsLoaded)
        loadCountriesInteractor.loadCountries(::onCountriesLoaded)
    }

    private fun onWeaponsLoaded(list: List<Weapon>) {
        Weapons.init(list)
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