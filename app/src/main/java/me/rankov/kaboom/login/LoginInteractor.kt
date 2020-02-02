package me.rankov.kaboom.login

import android.app.Activity
import android.util.Log
import com.amazonaws.mobile.client.*
import me.rankov.kaboom.App
import me.rankov.kaboom.prefs

class LoginInteractor {

    interface OnLoginListener {
        fun onSignedIn()
        fun onFail()
        fun onSignedOut()
    }

    fun getNickname(): String? {
        return prefs.nickname
    }

    fun getCountry(): Int {
        return prefs.country
    }

    fun signIn(activity: Activity) {
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
                UserState.SIGNED_IN -> {
                    Log.i("userState", "user is signed in")
                    listener.onSignedIn()
                }
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