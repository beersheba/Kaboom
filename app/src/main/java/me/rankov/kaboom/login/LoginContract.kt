package me.rankov.kaboom.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import me.rankov.kaboom.BasePresenter
import me.rankov.kaboom.BaseView

interface LoginContract {
    interface View: BaseView {
        fun signIn(signInIntent: Intent, requestCode: Int)
        fun navigateToMap()
        fun navigateToList()
        fun navigateToName(bundle: Bundle)
        fun navigateToCountry()
        fun navigateToHome()
    }

    interface Presenter : BasePresenter {
        fun onStart()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun onGoogleSignIn()
        fun onAmazonSignIn(activity: Activity)
        fun onSignOut()
    }
}