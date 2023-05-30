package com.nicolascastilla.challenge.di

import com.nicolascastilla.domain.usecases.FavoritesUseCase
import com.nicolascastilla.domain.usecases.GetGenereUseCase
import com.nicolascastilla.domain.usecases.GetTrendingUseCase
import com.nicolascastilla.domain.usecases.implementation.FavoritesUseCaseImpl
import com.nicolascastilla.domain.usecases.implementation.GetGenereListImpl
import com.nicolascastilla.domain.usecases.implementation.GetTrendingsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AplicationModule {


    @Binds
    abstract fun bindsGetTrendingsUseCases(useCaseImpl: GetTrendingsUseCaseImpl): GetTrendingUseCase

    @Binds
    abstract fun bindsGetGenereUseCases(useCaseImpl: GetGenereListImpl): GetGenereUseCase

    @Binds
    abstract fun bindsGetFavoritesUseCases(useCaseImpl: FavoritesUseCaseImpl): FavoritesUseCase



}