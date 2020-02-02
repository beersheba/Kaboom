package me.rankov.kaboom.login

import android.app.Activity
import android.os.Bundle
import me.rankov.kaboom.BasePresenter
import me.rankov.kaboom.BaseView

interface LoginContract {
    interface View : BaseView {
        fun navigateToList()
        fun navigateToName(bundle: Bundle)
        fun navigateToCountry()
        fun navigateToHome()
    }

    interface Presenter : BasePresenter {
        fun onSignIn(activity: Activity)
    }
}