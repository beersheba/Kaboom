package me.rankov.kaboom.country.details

import me.rankov.kaboom.BasePresenter
import me.rankov.kaboom.BaseView
import me.rankov.kaboom.model.Country

interface CountryDetailsContract {
    interface View : BaseView {
        fun setCountry(country: Country)
        fun goToStats(country: Country)
        fun showAction(url: String)
        fun showActionSelector(weapons: List<String>, attack: Boolean)
    }

    interface Presenter : BasePresenter {
        fun onActionClicked(attack: Boolean)
        fun onActionImageShown(country: Country)
        fun onItemSelected(id: Int, country: Country, attack: Boolean)
    }
}