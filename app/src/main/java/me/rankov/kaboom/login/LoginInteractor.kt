package me.rankov.kaboom.login

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import me.rankov.kaboom.App
import me.rankov.kaboom.R

class LoginInteractor {

    interface OnLoginListener {
        fun onSuccess(user: FirebaseUser?)
        fun onFail()
        fun onSignedOut()
    }

    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    fun initGoogleClient() {
        val context = App.applicationContext()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.debug_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
        auth = FirebaseAuth.getInstance()
    }

    fun getIntent(): Intent = googleSignInClient.signInIntent

    fun getCode() = RC_SIGN_IN

    fun getUser(): FirebaseUser? = auth.currentUser

    fun signIn(requestCode: Int, data: Intent?, listener: OnLoginListener) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!, listener)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(App.TAG, "Google sign in failed", e)
                listener.onFail()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, listener: OnLoginListener) {
        Log.d(App.TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        // [START_EXCLUDE silent]
        //showProgressDialog()
        // [END_EXCLUDE]

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(App.TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        Log.d(App.TAG, "" + user?.displayName)
                        Log.d(App.TAG, "" + user?.email)
                        Log.d(App.TAG, "" + user?.photoUrl.toString())
                        listener.onSuccess(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(App.TAG, "signInWithCredential:failure", task.exception)
                        Log.e(App.TAG, "Authentication Failed.")
                        listener.onFail()
                    }

                    // [START_EXCLUDE]
                    //hideProgressDialog()
                    // [END_EXCLUDE]
                }
    }

    fun signOut(listener: OnLoginListener) {

        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener {
            listener.onSignedOut()
        }
    }
}