package com.nicolascastilla.data.network.api

import com.nicolascastilla.entities.deezeresponse.DeezerResponse
import retrofit2.http.GET

interface ChallengeApi {

    @GET("chart/0/tracks")
    suspend fun searchSongs(): DeezerResponse
}