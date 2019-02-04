package me.rankov.kaboom.login

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import me.rankov.kaboom.BasePresenter

interface LoginContract {
    interface View {
        fun selectCountry()
        fun updateUI(user: FirebaseUser?)
        fun signIn(signInIntent: Intent, requestCode: Int)
    }

    interface Presenter : BasePresenter {
        fun onStart()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun onCountrySelect()
        fun onSignIn()
        fun onSignOut()
    }

}