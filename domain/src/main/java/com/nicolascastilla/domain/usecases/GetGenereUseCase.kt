package com.nicolascastilla.domain.usecases

import com.nicolascastilla.entities.Song
import kotlinx.coroutines.flow.Flow

interface GetGenereUseCase {
    suspend fun getGenereList(genere:String): Flow<List<Song>>

    suspend fun searchByText(text:String): Flow<List<Song>>
}