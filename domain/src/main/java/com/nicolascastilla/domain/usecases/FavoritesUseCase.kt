package com.nicolascastilla.domain.usecases

import com.nicolascastilla.entities.Song
import kotlinx.coroutines.flow.Flow

interface FavoritesUseCase {

    suspend fun setFavorite(song:Song)

     fun getFavorites(): Flow<List<Song>>

    suspend fun isFavorite(song:Song):Boolean

    suspend fun deleteFavorite(song:Song)


}