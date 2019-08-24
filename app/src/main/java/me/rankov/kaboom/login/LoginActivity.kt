package me.rankov.kaboom.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import me.rankov.kaboom.R
import me.rankov.kaboom.map.MapActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {
    override fun navigateToName(bundle: Bundle) {
        if (navController.currentDestination?.id == R.id.loginHomeFragment) {
            navController.navigate(R.id.actionHomeToName, bundle)
        }
    }

    override fun navigateToCountry() {
        if (navController.currentDestination?.id == R.id.loginHomeFragment) {
            navController.navigate(R.id.actionHomeToCountry)
        }
    }

    override fun navigateToHome() {
        navController.navigate(R.id.loginHomeFragment, null,
                NavOptions.Builder().setPopUpTo(R.id.login_nav, true).build()
        )
    }

    override fun navigateToMap() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }

    private val presenter = LoginPresenterImpl(this, LoginInteractor())

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signInButton.setOnClickListener { presenter.onSignIn() }
        signOutButton.setOnClickListener { presenter.onSignOut() }
        navController = findNavController(R.id.login_host_fragment)
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
}
