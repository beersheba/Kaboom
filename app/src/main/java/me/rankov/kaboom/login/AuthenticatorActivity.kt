package me.rankov.kaboom.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.auth.google.GoogleButton
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration
import com.amazonaws.mobile.auth.ui.SignInUI
import com.amazonaws.mobile.client.AWSMobileClient
import me.rankov.kaboom.MusicService
import me.rankov.kaboom.R
import me.rankov.kaboom.country.list.CountriesListActivity

class AuthenticatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backgroundMusic = Intent(this, MusicService::class.java)
        startService(backgroundMusic)

        AWSMobileClient.getInstance().initialize(this) {
            val config = AuthUIConfiguration.Builder()
                    .userPools(false) // show the Email and Password UI
                    .signInButton(GoogleButton::class.java) // Show Google
                    .logoResId(R.drawable.red_button) // Change the logo
                    .backgroundColor(Color.BLACK) // Change the background color
                    .isBackgroundColorFullScreen(true) // Full screen background color
                    .build()

            val signInUI = AWSMobileClient.getInstance()
                    .getClient(this@AuthenticatorActivity, SignInUI::class.java) as SignInUI

            signInUI.login(this@AuthenticatorActivity, CountriesListActivity::class.java)
                    .authUIConfiguration(config)
                    .execute()
        }.execute()
    }
}
