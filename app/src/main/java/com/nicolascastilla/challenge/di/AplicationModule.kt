package com.nicolascastilla.challenge.di

import android.app.Application
import android.content.Context
import com.nicolascastilla.challenge.compose.utils.MusicService
import com.nicolascastilla.challenge.compose.utils.ServiceManager
import com.nicolascastilla.data.RepositoryImp
import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.domain.usecases.GetTrendingUseCase
import com.nicolascastilla.domain.usecases.implementation.GetTrendingsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AplicationModule {

    //@Binds
    //abstract fun bindsChallengeRepository(repoImpl: RepositoryImp): ChallengeRepository

    @Binds
    abstract fun bindsGetTrendingsUseCases(useCaseImpl: GetTrendingsUseCaseImpl): GetTrendingUseCase

  /*  @Provides
    fun provideMusicService(): MusicService {
        return MusicService()
    }*/



}