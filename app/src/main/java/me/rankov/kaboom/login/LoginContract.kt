package me.rankov.kaboom.login

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import me.rankov.kaboom.BasePresenter

interface LoginContract {
    interface View {
        fun signIn(signInIntent: Intent, requestCode: Int)
        fun updateUI(user: FirebaseUser?)
        fun navigateToMain()
        fun navigateToRegister(fragmentId: Int)
        fun navigateToRegister(fragmentId: Int, bundle: Bundle)
        fun navigateToHome()
    }

    interface Presenter : BasePresenter {
        fun onStart()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun onSignIn()
        fun onSignOut()
    }
}