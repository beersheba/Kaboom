package me.rankov.kaboom.country_select.details

import me.rankov.kaboom.country_select.Country
import me.rankov.kaboom.country_select.CountryDetailsInteractor
import me.rankov.kaboom.country_select.details.CountryDetailsContract.Presenter
import me.rankov.kaboom.country_select.details.CountryDetailsContract.View

class CountryDetailsPresenterImpl(var countryView: View?,
                                  val countryDetailsInteractor: CountryDetailsInteractor,
                                  val country: Country) : Presenter {

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