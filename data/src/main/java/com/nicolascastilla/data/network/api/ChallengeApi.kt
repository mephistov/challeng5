package com.nicolascastilla.data.network.api

import com.nicolascastilla.entities.Song
import com.nicolascastilla.entities.deezeresponse.TrendingResponse
import retrofit2.http.GET

interface ChallengeApi {

    @GET("chart/0/tracks")
    suspend fun searchSongs(): TrendingResponse
}