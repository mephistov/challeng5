package com.nicolascastilla.data

import com.nicolascastilla.data.local.entities.FavoriteEntity
import com.nicolascastilla.data.local.mappers.toEntities
import com.nicolascastilla.data.local.mappers.toFavorite
import com.nicolascastilla.data.local.mappers.toSong
import com.nicolascastilla.entities.Album
import com.nicolascastilla.entities.Artist
import com.nicolascastilla.entities.Song
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import io.mockk.verify
import org.mockito.Mockito
import org.junit.Assert.assertEquals
import org.junit.Before

class MappersUnitTest {

    lateinit var album: Album
    lateinit var artist: Artist
    lateinit var favoriteEntity: FavoriteEntity

    @Before
    fun setup() {
        // Inicializamos nuestras instancias mockeadas.
        MockKAnnotations.init(this)

        album = mockk()
        artist = mockk()
        favoriteEntity = mockk()

        // Definimos el comportamiento esperado de nuestras instancias mockeadas.
        every { favoriteEntity.album } returns album
        every { favoriteEntity.artist } returns artist
        every { favoriteEntity.title } returns "Test Song"
        every { favoriteEntity.isFavorite } returns true
        every { favoriteEntity.duration } returns 200
        every { favoriteEntity.explicit_content_cover } returns 0
        every { favoriteEntity.explicit_content_lyrics } returns 0
        every { favoriteEntity.explicit_lyrics } returns false
        every { favoriteEntity.id } returns 1L
        every { favoriteEntity.link } returns "https://test.com"
        every { favoriteEntity.md5_image } returns "test_md5_image"
        every { favoriteEntity.position } returns 1
        every { favoriteEntity.preview } returns "https://test_preview.com"
        every { favoriteEntity.rank } returns 1
        every { favoriteEntity.title_short } returns "Test"
        every { favoriteEntity.title_version } returns "1.0"
        every { favoriteEntity.type } returns "Test Type"
    }

    @Test
    fun testFavoriteEntityToSongConversion() {


        val actualSong = favoriteEntity.toSong()

        assertEquals(favoriteEntity.album, actualSong.album)
        assertEquals(favoriteEntity.artist, actualSong.artist)
        assertEquals(favoriteEntity.title, actualSong.title)
        assertEquals(favoriteEntity.isFavorite, actualSong.isFavorite)

        verify {
            favoriteEntity.album
            favoriteEntity.artist
            favoriteEntity.title
            favoriteEntity.isFavorite
        }

    }

    @Test
    fun `test Song to FavoriteEntity conversion`() {
        val album = mockk<Album>()
        val artist = mockk<Artist>()
        val songEntity = mockk<Song>()

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

        val favoriteEntoty = songEntity.toFavorite()

        assertEquals(songEntity.album, favoriteEntoty.album)
        assertEquals(songEntity.artist, favoriteEntoty.artist)
        assertEquals(songEntity.title, favoriteEntoty.title)
        assertEquals(songEntity.isFavorite, favoriteEntoty.isFavorite)
    }

    @Test
    fun `test ListFavoriteEntity to ListSong conversion`() {


        val favoriteEntities = listOf(favoriteEntity, favoriteEntity)
        val songs = favoriteEntities.toEntities()

        assertEquals(favoriteEntities.size, songs.size)

        for (i in favoriteEntities.indices) {
            assertEquals(favoriteEntities[i].album, songs[i].album)
            assertEquals(favoriteEntities[i].artist, songs[i].artist)

        }
    }
}