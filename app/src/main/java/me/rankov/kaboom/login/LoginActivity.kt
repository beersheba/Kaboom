package me.rankov.kaboom.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import me.rankov.kaboom.R
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), LoginContract.View {

    override fun navigateToRegister(fragmentId: Int) {
        findNavController(R.id.login_host_fragment).navigate(fragmentId, null, options)
    }

    override fun navigateToMain() {
        toast("Going to main screen") //To change body of created functions use File | Settings | File Templates.
    }

    private val presenter = LoginPresenterImpl(this, LoginInteractor())

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

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
}
