package me.rankov.kaboom.login

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_login.*
import me.rankov.kaboom.GlideApp
import me.rankov.kaboom.MusicService
import me.rankov.kaboom.R
import me.rankov.kaboom.country.list.CountriesListActivity
import me.rankov.kaboom.map.MapActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {

    override fun setBackground() {
        GlideApp.with(this).load(R.drawable.earth).centerCrop().into(login_background)
    }

    override fun navigateToList() {
        val intent = Intent(this, CountriesListActivity::class.java)
        startActivity(intent)
    }

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

    private lateinit var backgroundMusic: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signInButton.setOnClickListener {
            presenter.onAmazonSignIn(this)
        }
        signOutButton.setOnClickListener { presenter.onSignOut() }
        navController = findNavController(R.id.login_host_fragment)
        backgroundMusic = Intent(this, MusicService::class.java)
        startService(backgroundMusic)
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

    override fun updateUI(signedIn: Boolean) {
        signInButton.visibility = if (signedIn) GONE else VISIBLE
        signOutButton.visibility = if (signedIn) VISIBLE else GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
    }
}
