package com.nicolascastilla.data

import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.data.local.mappers.toEntities
import com.nicolascastilla.data.local.mappers.toFavorite
import com.nicolascastilla.data.local.mappers.toSong
import com.nicolascastilla.data.network.ChallengeService
import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.entities.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val dataBase: ChallengeDao,
    private val apiService: ChallengeService
): ChallengeRepository {
//--------- network ----------
    override suspend fun getNetworkTrendings(): List<Song> {
        return apiService.getTrendings()
    }

    override suspend fun getGenere(genre: String): List<Song> {
        return apiService.getGenre(genre)
    }

    override suspend fun searchByText(text: String): List<Song> {
        return apiService.searchByText(text)
    }

    //------------ database --------
    override suspend fun setFavorite(song: Song) {
        dataBase.insertFavorite(song.toFavorite())
    }

    override fun getAllFavorites(): Flow<List<Song>> {
        return dataBase.getAllFavorites().map {
            it.toEntities()
        }
    }

    override suspend fun getFavoriteById(id: Long): Song? {
        return dataBase.getFavoriteById(id)?.toSong()
    }

    override suspend fun delete(song: Song) {
        dataBase.delete(song.toFavorite())
    }


}