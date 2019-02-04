package me.rankov.kaboom.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import me.rankov.kaboom.R

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private val presenter = LoginPresenterImpl(this, LoginInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signInButton.setOnClickListener { presenter.onSignIn() }
        signOutButton.setOnClickListener { presenter.onSignOut() }
        presenter.onCreate()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun signIn(signInIntent: Intent, requestCode: Int) {
        startActivityForResult(signInIntent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            signInButton.visibility = View.GONE
            signOutButton.visibility = View.VISIBLE
        } else {
            signInButton.visibility = View.VISIBLE
            signOutButton.visibility = View.GONE
        }
    }

    override fun selectCountry() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
