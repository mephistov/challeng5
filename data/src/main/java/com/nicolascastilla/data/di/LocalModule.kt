package com.nicolascastilla.data.di

import com.nicolascastilla.data.RepositoryImp
import com.nicolascastilla.domain.repositories.ChallengeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    @Binds
    abstract fun bindsReminderRepository(repoImpl: RepositoryImp): ChallengeRepository
}