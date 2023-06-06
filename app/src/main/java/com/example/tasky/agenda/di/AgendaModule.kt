package com.example.tasky.agenda.di

import com.example.tasky.agenda.data.DateGeneratorImpl
import com.example.tasky.agenda.data.remote.AgendaRepositoryImpl
import com.example.tasky.agenda.data.remote.AgendaService
import com.example.tasky.agenda.domain.AgendaRepository
import com.example.tasky.agenda.domain.DateGenerator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class AgendaModule {

    @Provides
    fun providesAgendaRepository(retrofit: Retrofit): AgendaService {
        return retrofit.create(AgendaService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
sealed class AgendaBindsModule {

    @Binds
    abstract fun bindMonthGenerator(monthGeneratorImpl: DateGeneratorImpl): DateGenerator

    @Binds
    abstract fun bindAgendaRepository(agendaRepositoryImpl: AgendaRepositoryImpl): AgendaRepository
}
