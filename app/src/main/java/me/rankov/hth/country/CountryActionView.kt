package me.rankov.hth.country

interface CountryActionView {
    fun setCountry(country: Country)
    fun heal(country: Country)
    fun attack(country: Country)
}
