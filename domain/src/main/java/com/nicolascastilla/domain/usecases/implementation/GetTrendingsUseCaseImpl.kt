package com.nicolascastilla.domain.usecases.implementation

import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.domain.usecases.GetTrendingUseCase
import com.nicolascastilla.entities.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrendingsUseCaseImpl @Inject constructor(
    private val repository: ChallengeRepository
    ) : GetTrendingUseCase {

    override suspend fun getAllTrendingsSongs(): Flow<List<Song>>  = flow {
        try {
            // Realiza la llamada a la API de Deezer para obtener las canciones populares
            val response = repository.getNetworkTrendings()

            // Emite el resultado al flujo de datos
            emit(response)
        } catch (e: Exception) {
            // Maneja cualquier error que pueda ocurrir durante la llamada a la API
            // ...
        }
    }

    override suspend fun getAllTrendingsSongsTest(): List<Song> {
        return repository.getNetworkTrendings()
    }


}