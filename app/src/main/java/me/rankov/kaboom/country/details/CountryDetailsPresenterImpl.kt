package me.rankov.kaboom.country.details

import me.rankov.kaboom.country.Country
import me.rankov.kaboom.country.details.CountryDetailsContract.Presenter
import me.rankov.kaboom.country.details.CountryDetailsContract.View

class CountryDetailsPresenterImpl(var countryView: View?,
                                  val countryDetailsInteractor: CountryDetailsInteractor,
                                  val country: Country) : Presenter {

    override fun onCreate() {
        countryView?.setBackground()
        countryView?.setCountry(country)
    }

    override fun onActionClicked(attack: Boolean) {
        val weaponNames =
                if (attack) Weapons.getWeaponsNames()
                else Weapons.getCuresNames()
        countryView?.showWeaponSelector(weaponNames, attack)
    }


    override fun onWeaponSelected(id: Int, country: Country, attack: Boolean) {
        if (attack) {
            countryDetailsInteractor.attack(country)
        } else {
            countryDetailsInteractor.heal(country)
        }
        countryView?.goToStats(country)
    }

    override fun onDestroy() {
        countryView = null
    }
}