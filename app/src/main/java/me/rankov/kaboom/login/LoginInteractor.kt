package me.rankov.kaboom.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.amazonaws.mobile.client.*
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
import me.rankov.kaboom.prefs

class LoginInteractor {

    interface OnLoginListener {
        fun onSuccess(user: FirebaseUser?)
        fun onSuccess()
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
        // AWS sign out
        AWSMobileClient.getInstance().signOut()
    }

    fun getNickname(): String? {
        return prefs.nickname
    }

    fun getCountry(): Int {
        return prefs.country
    }

    fun signInWithAmazon(activity: Activity, listener: OnLoginListener) {
        val hostedUIOptions = HostedUIOptions.builder()
                .scopes("openid", "email")
                .identityProvider("Google")
                .build()

        val signInUIOptions = SignInUIOptions.builder()
                .hostedUIOptions(hostedUIOptions)
                .build()

        AWSMobileClient.getInstance().showSignIn(activity, signInUIOptions, object : Callback<UserStateDetails?> {
            override fun onResult(details: UserStateDetails?) {
                Log.d(App.TAG, "onResult: " + details?.userState)
                try {
                    Log.d(App.TAG, AWSMobileClient.getInstance().username)
                    AWSMobileClient.getInstance().userAttributes.forEach {
                        Log.d(App.TAG, it.key + ":" + it.value)
                    }
                } catch (e: Exception) {
                    Log.e(App.TAG, "", e)
                }
                listener.onSuccess()
            }

            override fun onError(e: Exception?) {
                Log.e(App.TAG, "onError: ", e)
            }
        })
    }

    fun initAwsClient(listener: OnLoginListener) {
        AWSMobileClient.getInstance().initialize(App.applicationContext(), object : Callback<UserStateDetails?> {
            override fun onResult(userStateDetails: UserStateDetails?) {
                Log.i(App.TAG, userStateDetails?.userState.toString())
            }

            override fun onError(e: Exception?) {
                Log.e(App.TAG, "Error during initialization", e)
            }
        })

        AWSMobileClient.getInstance().addUserStateListener { userStateDetails ->
            when (userStateDetails.userState) {
                UserState.GUEST -> Log.i("userState", "user is in guest mode")
                UserState.SIGNED_OUT -> {
                    Log.i("userState", "user is signed out")
                    listener.onSignedOut()
                }
                UserState.SIGNED_IN -> Log.i("userState", "user is signed in")
                UserState.SIGNED_OUT_USER_POOLS_TOKENS_INVALID -> {
                    Log.i("userState", "need to login again")
                    listener.onSignedOut()
                }
                UserState.SIGNED_OUT_FEDERATED_TOKENS_INVALID -> {
                    Log.i("userState", "user logged in via federation, but currently needs new tokens")
                    listener.onSignedOut()
                }
                else -> {
                    Log.e("userState", "unsupported")
                    listener.onSignedOut()
                }
            }
        }
    }

}