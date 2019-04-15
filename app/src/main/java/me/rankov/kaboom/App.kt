package me.rankov.kaboom

import android.app.Application
import android.content.Context

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
    }
}