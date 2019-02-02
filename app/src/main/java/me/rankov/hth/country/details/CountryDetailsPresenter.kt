package me.rankov.hth.country.details

import me.rankov.hth.country.Country

interface CountryDetailsPresenter {
    fun onCreate()
    fun onAttackClicked(country: Country)
    fun onHealClicked(country: Country)
    fun onDestroy()
}