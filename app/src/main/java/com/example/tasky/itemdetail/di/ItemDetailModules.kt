package com.example.tasky.itemdetail.di

import com.example.tasky.itemdetail.data.EventRepositoryImpl
import com.example.tasky.itemdetail.domain.EventRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ItemDetailBindModule {

    @Binds
    abstract fun providesEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository
}