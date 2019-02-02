package me.rankov.hth.country.details

import me.rankov.hth.model.Country

interface CountryDetailsPresenter {
    fun onCreate()
    fun onAttackClicked(country: Country)
    fun onHealClicked(country: Country)
    fun onDestroy()
}