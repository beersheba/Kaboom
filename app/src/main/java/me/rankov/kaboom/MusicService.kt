package me.rankov.kaboom

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

const val ACTION_PLAY: String = "PLAY"
const val ACTION_PAUSE: String = "PAUSE"
const val ACTION_STOP: String = "STOP"

class MusicService : Service() {
    private var player: MediaPlayer? = null

    override fun onCreate() {
        player = MediaPlayer.create(this, R.raw.back_music)
        player?.apply {
            isLooping = true
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                player?.start()
            }
            ACTION_PAUSE -> {
                player?.pause()
            }
            ACTION_STOP -> {
                player?.stop()
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        player?.stop()
        player?.release()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}
