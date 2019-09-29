package me.rankov.kaboom.country.details

import me.rankov.kaboom.country.Country

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