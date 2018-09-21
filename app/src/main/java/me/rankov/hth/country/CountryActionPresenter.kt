package me.rankov.hth.me.rankov.hth.country

import me.rankov.hth.country.Country
import me.rankov.hth.country.CountryActionInteractor
import me.rankov.hth.country.CountryActionView

class CountryActionPresenter(var countryView: CountryActionView?,
                             val countryActionInteractor: CountryActionInteractor,
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