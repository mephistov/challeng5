package com.nicolascastilla.domain.repositories

import com.nicolascastilla.entities.Song
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getNetworkTrendings():List<Song>

    suspend fun getGenere(genere:String):List<Song>

    suspend fun searchByText(text:String):List<Song>
//---------
    suspend fun setFavorite(song:Song)
     fun getAllFavorites(): Flow<List<Song>>

    suspend fun getFavoriteById(id:Long): Song?

    suspend fun delete(song: Song)

}