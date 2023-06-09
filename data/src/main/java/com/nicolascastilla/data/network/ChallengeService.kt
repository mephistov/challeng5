package com.nicolascastilla.data.network

import com.nicolascastilla.data.network.api.ChallengeApi
import com.nicolascastilla.entities.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class ChallengeService @Inject constructor(private val api: ChallengeApi) {

    suspend fun getTrendings():List<Song>{
        return withContext(Dispatchers.IO) {
             api.searchSongs().data
        }
    }

    suspend fun getGenre(genre: String): List<Song> {
        return withContext(Dispatchers.IO) {
            api.searchGenre(genre).data
        }
    }

    suspend fun searchByText(text:String): List<Song> {
        return withContext(Dispatchers.IO) {
            api.searchGenre(text).data
        }
    }

}