package com.nicolascastilla.challenge.di

import com.nicolascastilla.domain.usecases.GetTrendingUseCase
import com.nicolascastilla.domain.usecases.implementation.GetTrendingsUseCaseImpl
import dagger.Binds
import dagger.Module
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