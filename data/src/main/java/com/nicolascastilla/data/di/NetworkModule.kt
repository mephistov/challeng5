package com.nicolascastilla.data.di

import com.nicolascastilla.data.network.api.ChallengeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    var logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    var httpClient: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logging)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.deezer.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideChallengeApi(retrofit: Retrofit): ChallengeApi {
        return retrofit.create(ChallengeApi::class.java)
    }
}