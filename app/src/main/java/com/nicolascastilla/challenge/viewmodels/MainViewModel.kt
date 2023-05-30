package com.nicolascastilla.challenge.viewmodels

import android.media.MediaPlayer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolascastilla.challenge.utils.ServiceManager
import com.nicolascastilla.challenge.utils.Utils
import com.nicolascastilla.domain.usecases.FavoritesUseCase
import com.nicolascastilla.domain.usecases.GetGenereUseCase
import com.nicolascastilla.domain.usecases.GetTrendingUseCase
import com.nicolascastilla.entities.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTrendingsUseCase: GetTrendingUseCase,
    private val getGenereUseCase: GetGenereUseCase,
    private val getFavoriteUseCase: FavoritesUseCase,
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
    val img1: MutableStateFlow<String> = MutableStateFlow("")
    val img2: MutableStateFlow<String> = MutableStateFlow("")





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
            val data = getTrendingsUseCase.getAllTrendingsSongs().map {list->
                list.map { item->
                    item.isFavorite = getFavoriteUseCase.isFavorite(item)
                    item
                }
            }
            data.collect {
                _myTrendings.value = it
                _loading.value = false
            }
        }

    }

    fun getGenereSearch(genere:String){
        isLoadingGenere.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val data = getGenereUseCase.getGenereList(genere).map {list->
                list.map { item->
                    item.isFavorite = getFavoriteUseCase.isFavorite(item)
                    item
                }
            }

            data.collect {
                genereList.value = it
                isLoadingGenere.value = false
            }
        }

    }
    fun searchByName(text: String) {
        isLoadingGenere.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val data = getGenereUseCase.searchByText(text).map {list->
                list.map { item->
                    item.isFavorite = getFavoriteUseCase.isFavorite(item)
                    item
                }
            }

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

    //----- favorites --------------------------------

    val favoritesList: Flow<List<Song>> = getFavoriteUseCase.getFavorites()

    fun removeFavourite(song: Song){
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteUseCase.deleteFavorite(song)
            //TODO actualizar la lista de vista anterior
        }
    }

    fun setFavorite(song: Song){
        viewModelScope.launch(Dispatchers.IO) {
            if(!song.isFavorite) {
                getFavoriteUseCase.setFavorite(song)
            } else {
                getFavoriteUseCase.deleteFavorite(song)
            }

        }
        updateList(song)
    }

    fun updateList(song: Song){
        val newIsFavorite = !song.isFavorite
        val updatedSong = song.copy(isFavorite = newIsFavorite)
        val updatedList = genereList.value.map {
            if (it.id == song.id) updatedSong else it
        }
        genereList.value = updatedList

        val updatedListT = _myTrendings.value.map {
            if (it.id == song.id) updatedSong else it
        }
        _myTrendings.value = updatedListT
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

    val myData = mutableStateOf("Initial Data")

    fun updateUI(song: Song){
        currentTitle.value = song.title
        currentArtist.value = song.artist.name
        img1.value = song.artist.picture_big
        img2.value = song.album.cover_big
        isPlaying.value = true;
    }

    fun initMediaPlayer(song: Song) {
        updateUI(song)
        _player.value = musicManager.initPLayer(song,)
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
            it.setOnCompletionListener {
                nextSong()
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