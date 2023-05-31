package com.nicolascastilla.challenge.di

import com.nicolascastilla.challenge.utils.ServiceManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ServiceManagerEntryPoint {
    fun serviceManager(): ServiceManager
}