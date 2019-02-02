package me.rankov.hth.me.rankov.hth.country

import me.rankov.hth.country.Country
import me.rankov.hth.country.CountryDetailsInteractor
import me.rankov.hth.country.CountryDetailsView
import me.rankov.hth.country.details.CountryDetailsPresenter

class CountryDetailsPresenterImpl(var countryView: CountryDetailsView?,
                                  val countryDetailsInteractor: CountryDetailsInteractor,
                                  val country: Country) : CountryDetailsPresenter {

    override fun onCreate() {
        countryView?.setCountry(country)
    }

    override fun onAttackClicked(country: Country) {

    }

    override fun onHealClicked(country: Country) {
    }

    override fun onDestroy() {
        countryView = null
    }
}