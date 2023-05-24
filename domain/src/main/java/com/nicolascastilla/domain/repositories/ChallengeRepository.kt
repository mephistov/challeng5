package com.nicolascastilla.domain.repositories

import com.nicolascastilla.entities.Song

interface ChallengeRepository {

    suspend fun getNetworkTrendings():List<Song>

}