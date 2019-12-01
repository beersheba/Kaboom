package me.rankov.kaboom.country.details

import me.rankov.kaboom.BasePresenter
import me.rankov.kaboom.BaseView
import me.rankov.kaboom.country.Country

interface CountryDetailsContract {
    interface View : BaseView {
        fun setCountry(country: Country)
        fun goToStats(country: Country)
        fun showWeaponSelector(weapons: List<String>, attack: Boolean)
    }

    interface Presenter : BasePresenter {
        fun onActionClicked(attack: Boolean)
        fun onWeaponSelected(id: Int, country: Country, attack: Boolean)
    }
}