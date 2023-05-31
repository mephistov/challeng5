package com.nicolascastilla.data.network.api

import com.nicolascastilla.entities.deezeresponse.DeezerResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChallengeApi {

    @GET("chart/0/tracks")
    suspend fun searchSongs(): DeezerResponse

    @GET("search")
    suspend fun searchGenre(@Query("q") genre: String): DeezerResponse

    @GET("search")
    suspend fun searchByText(@Query("q") genre: String): DeezerResponse
}