package com.nicolascastilla.domain.usecases

import com.nicolascastilla.entities.Song
import kotlinx.coroutines.flow.Flow

interface GetTrendingUseCase {

    suspend fun getAllTrendingsSongs(): Flow<List<Song>>
    suspend fun getAllTrendingsSongsTest(): List<Song>

}