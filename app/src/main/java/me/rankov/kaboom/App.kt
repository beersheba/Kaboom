package me.rankov.kaboom

import android.app.Application
import com.instabug.library.Instabug
import com.instabug.library.invocation.InstabugInvocationEvent



class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Instabug.Builder(this, "ed4eb5fa7787022e45d209759c1e2381")
                .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.SCREENSHOT)
                .build()
    }
}