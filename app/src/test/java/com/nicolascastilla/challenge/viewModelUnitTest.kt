package com.nicolascastilla.challenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nicolascastilla.challenge.utils.ServiceManager
import com.nicolascastilla.challenge.viewmodels.MainViewModel
import com.nicolascastilla.data.local.entities.FavoriteEntity
import com.nicolascastilla.domain.usecases.FavoritesUseCase
import com.nicolascastilla.domain.usecases.GetGenereUseCase
import com.nicolascastilla.domain.usecases.GetTrendingUseCase
import com.nicolascastilla.entities.Album
import com.nicolascastilla.entities.Artist
import com.nicolascastilla.entities.Song
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule


class viewModelUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var album: Album
    lateinit var artist: Artist
    lateinit var songEntity: Song

    @Before
    fun setup() {
        // Inicializamos nuestras instancias mockeadas.
        MockKAnnotations.init(this)

        album = mockk()
        artist = mockk()
        songEntity = mockk()

        // Definimos el comportamiento esperado de nuestras instancias mockeadas.
        every { songEntity.album } returns album
        every { songEntity.artist } returns artist
        every { songEntity.title } returns "Test Song"
        every { songEntity.isFavorite } returns true
        every { songEntity.duration } returns 200
        every { songEntity.explicit_content_cover } returns 0
        every { songEntity.explicit_content_lyrics } returns 0
        every { songEntity.explicit_lyrics } returns false
        every { songEntity.id } returns 1L
        every { songEntity.link } returns "https://test.com"
        every { songEntity.md5_image } returns "test_md5_image"
        every { songEntity.position } returns 1
        every { songEntity.preview } returns "https://test_preview.com"
        every { songEntity.rank } returns 1
        every { songEntity.title_short } returns "Test"
        every { songEntity.title_version } returns "1.0"
        every { songEntity.type } returns "Test Type"
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun searchForFavorite(){

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test tendings`() = runTest  {
        // Arrange
        val getTrendingUseCase = mockk<GetTrendingUseCase>()
        val getGenereUseCase = mockk<GetGenereUseCase>()
        val getFavoriteUseCase = mockk<FavoritesUseCase>()
        val musicManager = mockk<ServiceManager>()
        val song = mockk<Song>()

        every{songEntity.isFavorite = true} just Runs
        val songList = listOf(songEntity, songEntity, songEntity)
        val flowSongList = flowOf(songList)


        coEvery  { songEntity.isFavorite = getFavoriteUseCase.isFavorite(any()) } just Runs
        coEvery { getTrendingUseCase.getAllTrendingsSongs() } returns flowSongList
        coEvery { getFavoriteUseCase.isFavorite(any()) } returns true
        coEvery { getFavoriteUseCase.getFavorites() } returns flowSongList

        val viewModel = MainViewModel(getTrendingUseCase, getGenereUseCase, getFavoriteUseCase, musicManager)

        // Act
        viewModel.getTrendings()

        // Assert
        assertTrue(getFavoriteUseCase.isFavorite(song))
        assertTrue(viewModel.isLoading.value)  // should be true immediately after getTrendings is called
        advanceTimeBy(5000)  // advance time to allow the coroutine in getTrendings to complete
        assertFalse(viewModel.isLoading.value)  // should be false after the songs have been fetched
        assertEquals(songList, viewModel.myTrendings.value)
    }
}