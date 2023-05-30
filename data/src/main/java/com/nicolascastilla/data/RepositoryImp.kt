package com.nicolascastilla.data

import com.nicolascastilla.data.local.dao.ChallengeDao
import com.nicolascastilla.data.network.ChallengeService
import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.entities.Song
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val dataBase: ChallengeDao,
    private val apiService: ChallengeService
): ChallengeRepository {
    override suspend fun getNetworkTrendings(): List<Song> {
        return apiService.getTrendings()
    }

    override suspend fun getGenere(genre: String): List<Song> {
        return apiService.getGenre(genre)
    }


}