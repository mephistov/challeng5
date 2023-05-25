package com.nicolascastilla.challenge.viewmodels

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascastilla.domain.usecases.GetTrendingUseCase
import com.nicolascastilla.entities.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTrendingsUseCase: GetTrendingUseCase
): ViewModel() {

    private val _myTrendings: MutableStateFlow<List<Song>> = MutableStateFlow(emptyList())
    val myTrendings: StateFlow<List<Song>> = _myTrendings

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoadins: StateFlow<Boolean> = _loading

    private val _isTopViewVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isTopViewVisible: StateFlow<Boolean> = _isTopViewVisible

    private val _player = MutableLiveData<MediaPlayer?>()
    val player: LiveData<MediaPlayer?> = _player

    var currentSong: Song? = null

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

    fun updateCurrentSong(song: Song){
        currentSong = song
    }

    fun setViewVisibility(b: Boolean ) {
        _isTopViewVisible.value = b
        if(b){
            currentSong?.let {
                initMediaPlayer(it.preview)
                playPause()
            }
        }else{
            stop()
            currentSong = null
        }
    }

    //------- player ---
    fun initMediaPlayer(url: String) {
        _player.value = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setDataSource(url)
            prepareAsync() // Esto puede tomar tiempo para archivos grandes, por eso usamos prepareAsync() en lugar de prepare()
        }
    }

    fun playPause() {
        player.value?.let {
            if (it.isPlaying) {
                it.pause()
            } else {
                it.start()
            }
        }
    }

    fun stop() {
        player.value?.release()
        _player.value = null
    }


}