package com.nicolascastilla.challenge.viewmodels

import android.content.ComponentName
import android.content.ServiceConnection
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascastilla.challenge.compose.utils.MusicService
import com.nicolascastilla.challenge.compose.utils.ServiceManager
import com.nicolascastilla.domain.usecases.GetTrendingUseCase
import com.nicolascastilla.entities.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTrendingsUseCase: GetTrendingUseCase,
    private val musicService: ServiceManager
): ViewModel() {

    private val _myTrendings: MutableStateFlow<List<Song>> = MutableStateFlow(emptyList())
    val myTrendings: StateFlow<List<Song>> = _myTrendings

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoadins: StateFlow<Boolean> = _loading

    private val _isTopViewVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isTopViewVisible: StateFlow<Boolean> = _isTopViewVisible

    private val _player = MutableLiveData<MediaPlayer?>()
    val player: LiveData<MediaPlayer?> = _player

    val isPlaying: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val maxSizeSong: MutableStateFlow<Float> = MutableStateFlow(0.0f)
    val currentSongPosition: MutableStateFlow<Float> = MutableStateFlow(0.0f)
    val currentTitle: MutableStateFlow<String> = MutableStateFlow("")
    val currentArtist: MutableStateFlow<String> = MutableStateFlow("")

    var currentSong: Song? = null
    var listSongs: List<Song>? = null
    var currentSongPLaying : Int = 0
    var job: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO){
            musicService.init()
        }


    }

    fun getTrendings(){
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val data = getTrendingsUseCase.getAllTrendingsSongs()
            _loading.value = false
            data.collect {
                _myTrendings.value = it

            }
        }

    }

    fun updateCurrentSong(song: Song, cListSongs: List<Song>){
        currentSong = song
        listSongs = cListSongs
        currentSongPLaying = cListSongs.indexOf(song)
    }

    fun setViewVisibility(b: Boolean ) {
        _isTopViewVisible.value = b
        if(b){
            currentSong?.let {
                initMediaPlayer(it)
                playPause()
            }
        }else{
            stop()
        }
    }

    //------- player ---
    fun nextSong(){
        listSongs?.let {
            if(currentSongPLaying < (it.size)-1){
                currentSongPLaying++
                job?.cancel()
                stop()
                initMediaPlayer(it.get(currentSongPLaying))
            }
        }

    }
    fun prevSong(){
        listSongs?.let {
            if(currentSongPLaying >= 1){
                currentSongPLaying--
                job?.cancel()
                stop()
                initMediaPlayer(it.get(currentSongPLaying))
            }
        }
    }



    fun initMediaPlayer(song: Song) {
        currentTitle.value = song.title
        currentArtist.value = song.artist.name
        _player.value = musicService.initPLayer(song.preview)
        _player.value?.let {
            it.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.start()
                maxSizeSong.value = mediaPlayer.duration.toFloat()
                job = viewModelScope.launch(Dispatchers.IO) {
                    mediaPlayer?.let {
                        while (it.isPlaying) {
                            currentSongPosition.value = it.currentPosition.toFloat()
                            delay(1000) // Actualiza cada segundo
                        }
                    }

                }
            }
        }

       /* if(_player.value == null) {
            _player.value = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setDataSource(url)
                setOnPreparedListener { mediaPlayer ->
                    // Inicia la reproducción cuando el MediaPlayer esté preparado
                    mediaPlayer.start()
                    maxSizeSong.value = mediaPlayer.duration.toFloat()
                  /*  viewModelScope.launch(Dispatchers.IO) {
                        while (mediaPlayer.isPlaying) {
                            currentSongPosition.value = mediaPlayer.currentPosition.toFloat()
                            delay(1000) // Actualiza cada segundo
                        }
                    }*/

                }
                prepareAsync() // Esto puede tomar tiempo para archivos grandes, por eso usamos prepareAsync() en lugar de prepare()
            }
        }*/
    }


    fun playPause() {
        player.value?.let {
            if (it.isPlaying) {
                isPlaying.value = false
                it.pause()
            } else {
                isPlaying.value = true
                it.start()
            }

        }
    }

    fun stop() {
        job?.cancel()
        player.value?.release()
        _player.value = null
    }

    fun destroy() {
        musicService.unbindService()

    }


}