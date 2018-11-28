package me.rankov.hth.me.rankov.hth.country

import me.rankov.hth.country.Country
import me.rankov.hth.country.CountryDetailsInteractor
import me.rankov.hth.country.CountryDetailsView

class CountryDetailsPresenter(var countryView: CountryDetailsView?,
                              val countryDetailsInteractor: CountryDetailsInteractor,
                              val country: Country) {

    fun onCreate() {
        countryView?.setCountry(country)
    }

    fun onAttackClicked(country: Country) {

    }

    fun onHealClicked(country: Country) {
    }

    fun onDestroy() {
        countryView = null
    }
}