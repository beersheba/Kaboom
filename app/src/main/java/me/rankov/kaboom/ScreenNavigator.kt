package me.rankov.kaboom

import android.app.Activity
import android.widget.ImageView
import me.rankov.kaboom.country.CountryDetailsActivity
import me.rankov.kaboom.country.list.CountriesListActivity
import me.rankov.kaboom.login.LoginActivity
import me.rankov.kaboom.model.Country
import me.rankov.kaboom.stats.StatsActivity


class ScreenNavigator(var activity: Activity) {

    fun toLoginScreen() {
        LoginActivity.start(activity)
    }

    fun toCountriesListScreen() {
        CountriesListActivity.start(activity)
    }

    fun toCountryDetailsScreen(country: Country, image: ImageView) {
        CountryDetailsActivity.start(activity, country, image)
    }

    fun toStatsScreen() {
        StatsActivity.start(activity)
    }

}