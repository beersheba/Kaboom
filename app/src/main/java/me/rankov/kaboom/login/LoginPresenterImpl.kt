package me.rankov.kaboom.login

import android.app.Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginPresenterImpl(var loginView: LoginContract.View?, val loginInteractor: LoginInteractor) :
        LoginContract.Presenter, LoginInteractor.OnLoginListener {

    private var job: Job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    override fun onSignedOut() {
        scope.launch {
            loginView?.enableSignInUI()
            loginView?.navigateToHome()
        }
    }

    override fun onSignedIn() {
        scope.launch {
            loginView?.disableSignInUI()
            loginView?.navigateToList()
        }
    }

    override fun onFail() {
    }

    override fun onCreate() {
        loginInteractor.initAwsClient(this)
        loginView?.setBackground()
    }

    override fun onSignIn(activity: Activity) {
        loginInteractor.signIn(activity)
    }

    override fun detachView() {
        job.cancel()
        loginView = null
    }
}
