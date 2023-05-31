package com.nicolascastilla.domain

import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.domain.usecases.implementation.GetTrendingsUseCaseImpl
import com.nicolascastilla.entities.Album
import com.nicolascastilla.entities.Artist
import com.nicolascastilla.entities.Song
import io.mockk.MockKAnnotations
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Before

class GetTrendingsUseCaseImplTest {

    private val repository = mockk<ChallengeRepository>()
    private val useCase = GetTrendingsUseCaseImpl(repository)


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
    fun getAllTrendingsSongs_returnsExpectedResult() = runBlocking {
        // Arrange
        val expectedSongList = listOf(
            songEntity,
            songEntity,
            songEntity
        )
        coEvery { repository.getNetworkTrendings() } returns expectedSongList

        // Act
        val result = useCase.getAllTrendingsSongs().first()

        // Assert
        assertEquals(expectedSongList, result)
    }

    // ... otras pruebas ...

    @Test(expected = Exception::class)
    fun getAllTrendingsSongs_throwsException_whenRepositoryFails(): Unit = runBlocking {
        // Arrange
        coEvery { repository.getNetworkTrendings() } throws Exception()

        // Act & Assert
        useCase.getAllTrendingsSongs().first()
    }

    @Test(expected = Exception::class)
    fun getAllTrendingsSongsTest_throwsException_whenRepositoryFails(): Unit = runBlocking {
        // Arrange
        coEvery { repository.getNetworkTrendings() } throws Exception()

        // Act & Assert
        useCase.getAllTrendingsSongsTest()
    }
}