package me.rankov.kaboom.login

import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import me.rankov.kaboom.App
import org.jetbrains.anko.bundleOf

class LoginPresenterImpl(var loginView: LoginContract.View?, val loginInteractor: LoginInteractor) :
        LoginContract.Presenter, LoginInteractor.OnLoginListener {

    override fun onSignedOut() {
        loginView?.updateUI(null)
        loginView?.navigateToHome()
    }

    override fun onSuccess(user: FirebaseUser?) {
        loginView?.updateUI(user)
        checkRegistration(user)
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
        if (currentUser != null) {
            checkRegistration(currentUser)
        }
    }

    private fun checkRegistration(user: FirebaseUser?) {
        val nickname = loginInteractor.getNickname()
        val country = loginInteractor.getCountry()

        when {
            nickname.isEmpty() -> {
                val bundle = bundleOf("user" to user)
                loginView?.navigateToName(bundle)
            }
//            country < 0 -> loginView?.navigateToCountry()
//            else -> loginView?.navigateToMap()
            else -> loginView?.navigateToList()
        }
    }

    private fun getToken(user: FirebaseUser?) {
        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result?.token
                Log.d(App.TAG, "token: $token")
            }
        }
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
