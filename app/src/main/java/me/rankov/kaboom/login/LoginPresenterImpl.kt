package me.rankov.kaboom.login

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import me.rankov.kaboom.App
import me.rankov.kaboom.App.Companion.TAG
import me.rankov.kaboom.R

class LoginPresenterImpl(var loginView: LoginContract.View?, val loginInteractor: LoginInteractor) : LoginContract.Presenter {
    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate() {
        val context = App.applicationContext()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.debug_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
        auth = FirebaseAuth.getInstance()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                loginView?.updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        // [START_EXCLUDE silent]
        //showProgressDialog()
        // [END_EXCLUDE]

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        Log.d(TAG, "" + user?.displayName)
                        Log.d(TAG, "" + user?.email)
                        Log.d(TAG, "" + user?.photoUrl.toString())
                        loginView?.updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Log.e(TAG, "Authentication Failed.")
                        loginView?.updateUI(null)
                    }

                    // [START_EXCLUDE]
                    //hideProgressDialog()
                    // [END_EXCLUDE]
                }
    }

    override fun onStart() {
        val currentUser = auth.currentUser
        loginView?.updateUI(currentUser)
    }

    override fun onCountrySelect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        loginView?.signIn(signInIntent, RC_SIGN_IN)
    }

    override fun onSignOut() {
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener {
            loginView?.updateUI(null)
        }
    }

    override fun onDestroy() {
        loginView = null
    }
}
