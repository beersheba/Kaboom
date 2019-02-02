package me.rankov.hth.country

import me.rankov.hth.model.Country

interface CountryDetailsView {
    fun setCountry(country: Country)
    fun heal(country: Country)
    fun attack(country: Country)
}
