package me.rankov.kaboom

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager


val prefs: Prefs by lazy {
    App.prefs!!
}

class App : Application(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppInBackground() {
        sendStatus(0)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppInForeground() {
        sendStatus(1)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onAppDestroyed() {
        sendStatus(2)
    }

    private fun sendStatus(statusCounter: Int) {
        val intent = Intent(getString(R.string.MUSIC_ACTION))
        intent.putExtra(getString(R.string.MUSIC_STATUS), statusCounter)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

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
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}