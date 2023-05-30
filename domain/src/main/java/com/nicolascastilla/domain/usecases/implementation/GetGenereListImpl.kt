package com.nicolascastilla.domain.usecases.implementation

import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.domain.usecases.GetGenereUseCase
import com.nicolascastilla.entities.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenereListImpl@Inject constructor(
    private val repository: ChallengeRepository
):GetGenereUseCase {
    override suspend fun getGenereList(genere: String): Flow<List<Song>> = flow {
        try {
            val response = repository.getGenere(genere)
            emit(response)
        } catch (e: Exception) {
            // Maneja cualquier error que pueda ocurrir durante la llamada a la API
            // ...
        }
    }

    override suspend fun searchByText(text: String):  Flow<List<Song>> = flow {
        try {
            val response = repository.searchByText(text)
            emit(response)
        } catch (e: Exception) {
            // Maneja cualquier error que pueda ocurrir durante la llamada a la API
            // ...
        }
    }
}