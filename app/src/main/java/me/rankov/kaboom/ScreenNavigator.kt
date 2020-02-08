package me.rankov.kaboom

import android.app.Activity
import me.rankov.kaboom.country.list.CountriesListActivity
import me.rankov.kaboom.login.LoginActivity


class ScreenNavigator(var activity: Activity) {

    fun toLoginScreen() {
        LoginActivity.start(activity)
    }

    fun toCountriesListScreen() {
        CountriesListActivity.start(activity)
    }

}