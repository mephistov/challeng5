package com.nicolascastilla.challenge.utils

import android.content.*
import android.media.MediaPlayer
import android.os.IBinder
import com.nicolascastilla.entities.Song
import javax.inject.Inject


class ServiceManager @Inject constructor(
    private val context: Context,
): ServiceConnection {


    private lateinit var musicService: MusicService
    private var bound: Boolean = false
    private lateinit var intentService: Intent
    private var player: MediaPlayer? = null
    val notifManager by lazy {
        ChallengeNotificationManager(context)
    }



    fun init(){
        Intent(context, MusicService::class.java).also { intent->
            intentService = intent
            context.bindService(intent, this, Context.BIND_AUTO_CREATE)
        }

    }

    var external:Int = 0
    fun setOutsiderOptions(opc:Int){
        external = opc
    }

    fun getDuration():Int{
        if (bound) {
            musicService.getCurrentPosition()
        }
        return 0
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        // We've bound to MusicService, cast the IBinder and get MusicService instance
        val binder = service as MusicService.MusicBinder
        musicService = binder.getService()
        bound = true

        if(external ==1)
            playPause()
        else if(external ==2)
            outsiderPrev()
        else if(external ==3)
            outsiderNext()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        bound = false
    }

 /*   fun initPLayer(song: Song,  action: () -> Unit): MediaPlayer? {
        if (bound) {
            notifManager.setLocalNotification(true)
            return musicService.initPlayer(song.preview).apply {
                this?.let {
                    it.setOnCompletionListener {
                        outsiderNext()
                        action()
                    }
                }
            }
        }else
            return null
    }*/
    fun getMediaPlayer():MediaPlayer?{
         if (bound) {
             musicService.getPlayer()
         }
        return null
    }
    fun initPLayer(song: Song): MediaPlayer? {
        if (bound) {
            notifManager.setLocalNotification(true)
            return musicService.initPlayer(song.preview).apply {
                this?.let {
                    Utils.superUpdate()
                    it.setOnCompletionListener {
                        outsiderNext()
                    }
                }
            }
        }else
            return null
    }

    fun getCurrentPosition():Int{
        if (bound) {
            musicService.getCurrentPosition()
        }
        return 0
    }

    fun changeMusic(newSong:String){
        if (bound) {
            musicService.changeMusic(newSong)
        }
    }

    fun isPLaying():Boolean{
        if(bound){
            return musicService.isPlaying()
        }
        return false
    }

    fun playMusic(){
        if (bound) {
            musicService.playMusic()
            notifManager.setLocalNotification(true)

        }
    }

    fun stopMusic(){
        if (bound) {
            musicService.stopMusic()
            //context.stopService(intentService)
            //notifManager.setLocalNotification(currentSong,false)
        }
    }

    fun pauseMusic(){
        if (bound) {
            musicService.pauseMusic()
            notifManager.setLocalNotification(false)
        }
    }
    fun playPause() {
        if (bound) {
            if(isPLaying()){
                pauseMusic()
            }else{
                playMusic()
            }
        }
    }

    fun unbindService() {
        if (bound) {
            context.unbindService(this)
            bound = false
        }
    }

    fun outsiderNext(){
        Utils.listSongsPlayable?.let {
            var currentSongPLaying = it.indexOf(Utils.currentSong)
                if(currentSongPLaying < (it.size)-1){
                    currentSongPLaying++
                    stopMusic()
                    Utils.currentSong = it.get(currentSongPLaying)
                    initPLayer(it.get(currentSongPLaying))
                }

        }
    }


    fun outsiderPrev(){
        Utils.listSongsPlayable?.let {
            var currentSongPLaying = it.indexOf(Utils.currentSong)
            if(currentSongPLaying >= 1){
                currentSongPLaying--
                stopMusic()
                Utils.currentSong = it.get(currentSongPLaying)
                initPLayer(it.get(currentSongPLaying))
            }
        }
    }



}