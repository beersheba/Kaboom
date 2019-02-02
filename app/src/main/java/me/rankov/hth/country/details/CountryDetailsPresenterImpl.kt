package me.rankov.hth.me.rankov.hth.country

import me.rankov.hth.country.Country
import me.rankov.hth.country.CountryDetailsInteractor
import me.rankov.hth.country.details.CountryDetailsContract.Presenter
import me.rankov.hth.country.details.CountryDetailsContract.View

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