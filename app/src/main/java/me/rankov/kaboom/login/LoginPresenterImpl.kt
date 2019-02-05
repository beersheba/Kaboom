package me.rankov.kaboom.login

import android.content.Intent
import com.google.firebase.auth.FirebaseUser

class LoginPresenterImpl(var loginView: LoginContract.View?, val loginInteractor: LoginInteractor) :
        LoginContract.Presenter, LoginInteractor.OnLoginListener {
    override fun onSignedOut() {
        loginView?.updateUI(null)
    }

    override fun onSuccess(user: FirebaseUser?) {
        loginView?.updateUI(user)
    }

    override fun onFail() {
        loginView?.updateUI(null)
    }

    override fun onCreate() {
        loginInteractor.initGoogleClient()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loginInteractor.signIn(requestCode, data, this)
    }

    override fun onStart() {
        val currentUser = loginInteractor.getUser()
        loginView?.updateUI(currentUser)
    }

    override fun onCountrySelect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSignIn() {
        val signInIntent = loginInteractor.getIntent()
        val requestCode = loginInteractor.getCode()
        loginView?.signIn(signInIntent, requestCode)
    }

    override fun onSignOut() {
        loginInteractor.signOut(this)
    }

    override fun onDestroy() {
        loginView = null
    }
}
