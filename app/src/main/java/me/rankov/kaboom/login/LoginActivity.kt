package me.rankov.kaboom.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_login.*
import me.rankov.kaboom.GlideApp
import me.rankov.kaboom.MusicService
import me.rankov.kaboom.R
import me.rankov.kaboom.country.list.CountriesListActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private val presenter = LoginPresenterImpl(this, LoginInteractor())

    private lateinit var navController: NavController

    private lateinit var backgroundMusic: Intent

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signInButton.setOnClickListener {
            presenter.onSignIn(this)
        }
        navController = findNavController(R.id.login_host_fragment)
        backgroundMusic = Intent(this, MusicService::class.java)
        startService(backgroundMusic)
        presenter.onCreate()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

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

    override fun disableSignInUI() {
        signInButton.visibility = View.INVISIBLE
    }

    override fun enableSignInUI() {
        signInButton.visibility = View.VISIBLE
    }
}
