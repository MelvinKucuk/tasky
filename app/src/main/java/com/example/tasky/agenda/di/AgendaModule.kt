package com.example.tasky.agenda.di

import android.content.Context
import androidx.room.Room
import com.example.tasky.agenda.data.AgendaRepositoryImpl
import com.example.tasky.agenda.data.EventRepositoryImpl
import com.example.tasky.agenda.data.local.AgendaDao
import com.example.tasky.agenda.data.local.AgendaDatabase
import com.example.tasky.agenda.data.remote.AgendaService
import com.example.tasky.agenda.domain.AgendaRepository
import com.example.tasky.agenda.domain.EventRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AgendaModule {

    @Singleton
    @Provides
    fun providesAgendaDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context = context,
            AgendaDatabase::class.java,
            "agenda_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesAgendaDao(agendaDatabase: AgendaDatabase): AgendaDao = agendaDatabase.dao

    @Provides
    fun providesAgendaRepository(retrofit: Retrofit): AgendaService {
        return retrofit.create(AgendaService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
sealed class AgendaBindsModule {

    @Binds
    abstract fun bindAgendaRepository(agendaRepositoryImpl: AgendaRepositoryImpl): AgendaRepository

    @Binds
    abstract fun providesEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository
}
