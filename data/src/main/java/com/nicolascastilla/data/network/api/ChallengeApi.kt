package com.nicolascastilla.data.network.api

import com.nicolascastilla.entities.Song
import retrofit2.http.GET

interface ChallengeApi {

    @GET("chart/track")
    suspend fun searchSongs(): List<Song>
}