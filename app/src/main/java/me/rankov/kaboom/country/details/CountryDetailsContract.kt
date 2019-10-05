package me.rankov.kaboom.country.details

import me.rankov.kaboom.BasePresenter
import me.rankov.kaboom.BaseView
import me.rankov.kaboom.country.Country

interface CountryDetailsContract {
    interface View: BaseView {
        fun setCountry(country: Country)
        fun heal(country: Country)
        fun attack(country: Country)
    }

    interface Presenter: BasePresenter {
        fun onAttackClicked(country: Country)
        fun onHealClicked(country: Country)
    }
}