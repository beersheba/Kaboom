package me.rankov.kaboom.login

import android.app.Activity

class LoginPresenterImpl(var loginView: LoginContract.View?, val loginInteractor: LoginInteractor) :
        LoginContract.Presenter, LoginInteractor.OnLoginListener {


    override fun onSignedOut() {
        loginView?.navigateToHome()
    }

    override fun onSignedIn() {
        loginView?.navigateToList()
    }

    override fun onFail() {
    }

    override fun onCreate() {
        loginView?.setBackground()
        loginInteractor.initAwsClient(this)
    }

    override fun onSignIn(activity: Activity) {
        loginInteractor.signIn(activity)
    }

    override fun onDestroy() {
        loginView = null
    }
}
