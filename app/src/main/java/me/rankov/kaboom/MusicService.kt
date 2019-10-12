package me.rankov.kaboom

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MusicService : Service() {
    private var player: MediaPlayer? = null

    override fun onCreate() {
        preparePlayer()
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(stateReceiver, IntentFilter(getString(R.string.MUSIC_ACTION)))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player?.start()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        player?.stop()
        player?.release()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private val stateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getIntExtra(getString(R.string.MUSIC_STATUS), 2)) {
                0 -> player?.pause()
                1 ->
                    if (player != null)
                        player?.start()
                    else {
                        preparePlayer()
                        player?.start()
                    }
                else -> {
                    player?.stop()
                    player?.release()
                }
            }
        }
    }

    private fun preparePlayer() {
        player = MediaPlayer.create(this, R.raw.back_music)
        player?.apply {
            isLooping = true
            setVolume(0.2f, 0.2f)
        }
    }
}
