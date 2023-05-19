package com.nicolascastilla.data.di

import android.content.Context
import androidx.room.Room
import com.nicolascastilla.data.local.ChallengeDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val QUOTE_DATABASE_NAME = "challenge_database"


    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context = context,
            klass = ChallengeDataBase::class.java,
            name = QUOTE_DATABASE_NAME
        )
            //.addMigrations(MIGRATION_1_2)
            .build()

    @Singleton
    @Provides
    fun provideChallengeDao(db: ChallengeDataBase) = db.getChalengeDao()

}