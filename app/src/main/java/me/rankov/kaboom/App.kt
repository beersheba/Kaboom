package me.rankov.kaboom

import android.app.Application
import android.content.Context
import com.instabug.library.Instabug
import com.instabug.library.invocation.InstabugInvocationEvent

val prefs: Prefs by lazy {
    App.prefs!!
}

class App : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: App? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        const val TAG = "KaboomDebug"
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext())
        super.onCreate()
        Instabug.Builder(this, "ed4eb5fa7787022e45d209759c1e2381")
                .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.SCREENSHOT)
                .build()
    }
}