package com.nicolascastilla.challenge.viewmodels

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascastilla.challenge.utils.ServiceManager
import com.nicolascastilla.challenge.utils.Utils
import com.nicolascastilla.domain.usecases.GetGenereUseCase
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
    private val getGenereUseCase: GetGenereUseCase,
    private val musicManager: ServiceManager
): ViewModel() {

    private val _myTrendings: MutableStateFlow<List<Song>> = MutableStateFlow(emptyList())
    val myTrendings: StateFlow<List<Song>> = _myTrendings

    val genereList: MutableStateFlow<List<Song>> = MutableStateFlow(emptyList())

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

    val isLoadingGenere: MutableStateFlow<Boolean> = MutableStateFlow(true)

    init {
       /* viewModelScope.launch(Dispatchers.IO){
            musicManager.init()
        }*/
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

    fun getGenereSearch(genere:String){
        isLoadingGenere.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val data = getGenereUseCase.getGenereList(genere)
            data.collect {
                genereList.value = it
                isLoadingGenere.value = false
            }
        }

    }

    fun updateCurrentSong(song: Song, cListSongs: List<Song>){
        currentSong = song
        listSongs = cListSongs
        Utils.currentSong = currentSong
        Utils.listSongsPlayable = listSongs
        currentSongPLaying = cListSongs.indexOf(song)
    }

    fun setViewVisibility(b: Boolean ) {
        _isTopViewVisible.value = b
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
        isPlaying.value = true;
        _player.value = musicManager.initPLayer(song)
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
    }


    fun playPause() {
        musicManager.playPause()
        val testbool = musicManager.isPLaying()
        isPlaying.value = testbool
    }

    fun stop() {
        isPlaying.value = false
        job?.cancel()
        musicManager.stopMusic()
        player.value?.release()
        _player.value = null
    }

    fun destroy() {
        musicManager.unbindService()

    }


}