package com.nicolascastilla.challenge.utils

import android.app.*
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MusicService: Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return MusicBinder()
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    fun initPlayer(url: String): MediaPlayer? {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setOnPreparedListener {
                it.start()
            }
            setDataSource(url)
            prepareAsync()
        }
        return  mediaPlayer
    }

    fun changeMusic(song:String){
        mediaPlayer?.apply {
            stop()
            initPlayer(song)
        }

    }

    fun isPlaying():Boolean{
        mediaPlayer?.let {
            return it.isPlaying
        }
        return false
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun stopMusic() {
        mediaPlayer?.stop()
    }

    fun playMusic() {
        mediaPlayer?.start()
    }

    fun playPauseMusic(){
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            } else {
                it.start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
       /* createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification: Notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Music Player")
            .setContentText("Music is playing...")
            .setSmallIcon(R.drawable.music_icon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)*/

        // ...


        return START_STICKY
    }

 /*   fun createNotificationChannel() {

            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Music Player Channel",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                setSound(null, null)
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)

    }*/

    // ...

}