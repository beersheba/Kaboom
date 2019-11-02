package me.rankov.kaboom.country.details

import me.rankov.kaboom.country.Country
import me.rankov.kaboom.country.details.CountryDetailsContract.Presenter
import me.rankov.kaboom.country.details.CountryDetailsContract.View

class CountryDetailsPresenterImpl(var countryView: View?,
                                  val countryDetailsInteractor: CountryDetailsInteractor,
                                  val country: Country) : Presenter {

    override fun onCreate() {
        countryView?.setBackground()
        countryView?.setCountry(country)
    }

    override fun onAttackClicked(country: Country) {
        countryDetailsInteractor.attack(country)
        countryView?.attack(country)
    }

    override fun onHealClicked(country: Country) {
        countryDetailsInteractor.heal(country)
        countryView?.heal(country)
    }

    override fun onDestroy() {
        countryView = null
    }
}