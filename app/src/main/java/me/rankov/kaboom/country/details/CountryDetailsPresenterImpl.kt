package me.rankov.kaboom.country.details

import me.rankov.kaboom.country.Country
import me.rankov.kaboom.country.CountryDetailsInteractor
import me.rankov.kaboom.country.details.CountryDetailsContract.Presenter
import me.rankov.kaboom.country.details.CountryDetailsContract.View

class CountryDetailsPresenterImpl(var countryView: View?,
                                  val countryDetailsInteractor: CountryDetailsInteractor,
                                  val country: Country) : Presenter {

    override fun onCreate() {
        countryView?.setCountry(country)
    }

    override fun onAttackClicked(country: Country) {
        countryView?.attack(country)
    }

    override fun onHealClicked(country: Country) {
        countryView?.heal(country)
    }

    override fun onDestroy() {
        countryView = null
    }
}