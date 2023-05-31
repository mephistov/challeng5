package com.nicolascastilla.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nicolascastilla.data.local.ChallengeDataBase
import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.data.local.entities.FavoriteEntity
import com.nicolascastilla.data.local.mappers.toFavorite
import com.nicolascastilla.entities.Album
import com.nicolascastilla.entities.Artist
import com.nicolascastilla.entities.Song
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DaoDBTest {

    private lateinit var db: ChallengeDataBase
    private lateinit var challengeDao: ChallengeDao

    val testAlbum = Album(
        cover = "testCover",
        cover_big = "testCoverBig",
        cover_medium = "testCoverMedium",
        cover_small = "testCoverSmall",
        cover_xl = "testCoverXl",
        id = 1,
        md5_image = "testMd5Image",
        title = "testTitle",
        tracklist = "testTracklist",
        type = "testType"
    )
    val testArtist = Artist(
        id = 1,
        link = "testLink",
        name = "testName",
        picture = "testPicture",
        picture_big = "testPictureBig",
        picture_medium = "testPictureMedium",
        picture_small = "testPictureSmall",
        picture_xl = "testPictureXl",
        radio = true,
        tracklist = "testTracklist",
        type = "testType"
    )
    val testSong = Song(
        album = testAlbum,
        artist = testArtist,
        duration = 300,
        explicit_content_cover = 1,
        explicit_content_lyrics = 1,
        explicit_lyrics = true,
        id = 1L,
        link = "testLink",
        md5_image = "testMd5Image",
        position = 1,
        preview = "testPreview",
        rank = 1,
        title = "testTitle",
        title_short = "testTitleShort",
        title_version = "testTitleVersion",
        type = "testType",
        isFavorite = false
    )

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ChallengeDataBase::class.java).build()
        challengeDao = db.getChalengeDao()


    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testInsertAndGetFavorite() = runBlocking {
        val favorite = testSong.toFavorite()
        challengeDao.insertFavorite(favorite)

        val loaded = challengeDao.getFavoriteById(favorite.id)
        assertEquals(favorite, loaded)
    }


    @Test
    fun testGetAllFavorites() = runBlocking {
        val favorites = listOf(
            testSong.toFavorite(),
            testSong.copy(id = 2L).toFavorite(),
            testSong.copy(id = 3L).toFavorite(),

        )
        favorites.forEach { challengeDao.insertFavorite(it) }

        val allFavorites = challengeDao.getAllFavorites().first()
        assertEquals(favorites, allFavorites)
    }

    @Test
    fun testDeleteFavorite() = runBlocking {
        val favorite = testSong.toFavorite()
        challengeDao.insertFavorite(favorite)

        challengeDao.delete(favorite)
        val loaded = challengeDao.getFavoriteById(favorite.id)
        assertNull(loaded)
    }
}