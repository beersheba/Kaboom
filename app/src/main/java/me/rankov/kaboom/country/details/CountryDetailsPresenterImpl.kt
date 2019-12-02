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
        val itemNames =
                if (attack) ActionItems.getWeaponsNames()
                else ActionItems.getCuresNames()
        countryView?.showActionSelector(itemNames, attack)
    }

    override fun onActionImageShown(country: Country) {
        countryView?.goToStats(country)
    }


    override fun onItemSelected(id: Int, country: Country, attack: Boolean) {
        val baseUrl = "https://hands-of-god.s3-us-west-2.amazonaws.com/images/weapons/"
        val actionImage: String = if (attack) {
            countryDetailsInteractor.attack(country)
            ActionItems.getWeapons()[id].image
        } else {
            countryDetailsInteractor.heal(country)
            ActionItems.getCures()[id].image
        }
        countryView?.showAction(baseUrl.plus(actionImage))
    }

    override fun onDestroy() {
        countryView = null
    }
}