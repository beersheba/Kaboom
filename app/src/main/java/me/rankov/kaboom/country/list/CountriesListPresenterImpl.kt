package me.rankov.kaboom.country.list

import android.widget.ImageView
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.auth.core.SignInStateChangeListener
import com.amazonaws.mobile.client.AWSMobileClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.rankov.kaboom.country.details.ActionItems
import me.rankov.kaboom.country.list.CountriesListContract.Presenter
import me.rankov.kaboom.country.list.CountriesListContract.View
import me.rankov.kaboom.model.ActionItem
import me.rankov.kaboom.model.Country

class CountriesListPresenterImpl(var countriesListView: View?,
                                 val loadCountriesInteractor: CountriesListLoadInteractor) : Presenter {

    override fun onCreate() {
        countriesListView?.setBackground()
        countriesListView?.showProgress()
        loadCountriesInteractor.loadActionItems(::onActionItemsLoaded)
        loadCountriesInteractor.loadCountries(::onCountriesLoaded)
        GlobalScope.launch {
            loadCountriesInteractor.logAmazonTokens()
        }

        IdentityManager.getDefaultIdentityManager().addSignInStateChangeListener(
                object : SignInStateChangeListener {
                    override fun onUserSignedIn() {
                    }

                    override fun onUserSignedOut() {
                        countriesListView?.navigateToHome()
                    }
                }
        )
    }

    private fun onActionItemsLoaded(list: List<ActionItem>) {
        ActionItems.init(list)
    }

    override fun onCountriesLoaded(countries: List<Country>) {
        val countriesByPopulation = sortCountriesByPopulation(countries)
        countriesListView?.setCountries(countriesByPopulation)
        countriesListView?.hideProgress()
    }

    private fun sortCountriesByPopulation(countries: List<Country>): List<Country> {
        return countries.sortedWith(compareByDescending { it.population })
    }

    override fun onCountryClicked(country: Country, imageView: ImageView) {
        countriesListView?.navigateToCountry(country, imageView)
    }

    override fun onSignOut() {
        AWSMobileClient.getInstance().signOut()
    }

    override fun detachView() {
        countriesListView = null
    }
}