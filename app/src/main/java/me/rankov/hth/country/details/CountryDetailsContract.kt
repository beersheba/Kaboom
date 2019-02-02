package me.rankov.hth.country.details

import me.rankov.hth.country.Country

interface CountryDetailsContract {

    interface View {
        fun setCountry(country: Country)
        fun heal(country: Country)
        fun attack(country: Country)
    }

    interface Presenter {
        fun onCreate()
        fun onAttackClicked(country: Country)
        fun onHealClicked(country: Country)
        fun onDestroy()
    }
}