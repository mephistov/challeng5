package com.nicolascastilla.challenge.compose.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.IBinder
import javax.inject.Inject

class ServiceManager @Inject constructor(
    private val context: Context,
): ServiceConnection {

    private lateinit var musicService: MusicService
    private var bound: Boolean = false

    fun init(){
        Intent(context,MusicService::class.java).also {intent->
            //context.startService(intent)
            context.bindService(intent, this, Context.BIND_AUTO_CREATE)
        }

    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        // We've bound to MusicService, cast the IBinder and get MusicService instance
        val binder = service as MusicService.MusicBinder
        musicService = binder.getService()
        bound = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        bound = false
    }

    fun initPLayer(url:String): MediaPlayer? {
        if (bound) {
            return musicService.initPlayer(url)
        }else
            return null
    }

    fun playMusic(){
        if (bound) {
            musicService.playMusic()
        }
    }

    fun unbindService() {
        if (bound) {
            context.unbindService(this)
            bound = false
        }
    }

}