package com.nicolascastilla.challenge.di

import android.app.Application
import android.content.Context
import com.nicolascastilla.challenge.utils.ServiceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AndroidModules {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideServiceManager(context: Context): ServiceManager {
        return ServiceManager(context).apply {
            init()
        }
    }
}