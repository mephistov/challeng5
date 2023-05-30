package com.nicolascastilla.domain.usecases.implementation

import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.domain.usecases.FavoritesUseCase
import com.nicolascastilla.entities.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesUseCaseImpl @Inject constructor(
    private val repository: ChallengeRepository
):FavoritesUseCase {

    override suspend fun setFavorite(song: Song) {
        repository.setFavorite(song)
    }

    override  fun getFavorites(): Flow<List<Song>> {
        return repository.getAllFavorites()
    }

    override suspend fun isFavorite(song: Song): Boolean {
        repository.getFavoriteById(song.id) ?: return false

        return true;

    }

    override suspend fun deleteFavorite(song: Song) {
        repository.delete(song)
    }
}