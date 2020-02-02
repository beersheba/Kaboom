package me.rankov.kaboom.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import me.rankov.kaboom.App
import org.jetbrains.anko.bundleOf

class LoginPresenterImpl(var loginView: LoginContract.View?, val loginInteractor: LoginInteractor) :
        LoginContract.Presenter, LoginInteractor.OnLoginListener {

    override fun onSignedOut() {
        loginView?.navigateToHome()
    }

    override fun onSuccess(user: FirebaseUser?) {
        checkRegistration(user)
    }

    override fun onSignedIn() {
        loginView?.navigateToList()
    }

    override fun onFail() {
    }

    override fun onCreate() {
        loginView?.setBackground()
        loginInteractor.initGoogleClient()
        loginInteractor.initAwsClient(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loginInteractor.signIn(requestCode, data, this)
    }

    override fun onStart() {}

    private fun checkRegistration(user: FirebaseUser?) {
        val nickname = loginInteractor.getNickname()
        val country = loginInteractor.getCountry()

        when {
            nickname.isNullOrBlank() -> {
                val bundle = bundleOf("user" to user)
                loginView?.navigateToName(bundle)
            }
//            country < 0 -> loginView?.navigateToCountry()
//            else -> loginView?.navigateToMap()
            else -> loginView?.navigateToList()
        }
    }

    private fun getFirebaseToken(user: FirebaseUser?) {
        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result?.token
                Log.d(App.TAG, "token: $token")
            }
        }
    }

    override fun onGoogleSignIn() {
        val signInIntent = loginInteractor.getIntent()
        val requestCode = loginInteractor.getCode()
        loginView?.signIn(signInIntent, requestCode)
    }

    override fun onAmazonSignIn(activity: Activity) {
        loginInteractor.signInWithAmazon(activity, this)
    }

    override fun onSignOut() {
        loginInteractor.signOut(this)
    }

    override fun onDestroy() {
        loginView = null
    }
}
