package me.rankov.kaboom.login

import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient

class AuthIntentHandlingActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        val activityIntent = intent
        if (activityIntent.data != null && "kaboom" == activityIntent.data!!.scheme) {
            AWSMobileClient.getInstance().handleAuthResponse(activityIntent)
        }
    }
}
