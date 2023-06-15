@file:SuppressLint("NewApi")

package com.example.tasky.agenda.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.agenda.domain.AgendaRepository
import com.example.tasky.agenda.domain.DateGenerator
import com.example.tasky.agenda.domain.GetInitialsUseCase
import com.example.tasky.agenda.presentation.util.AddNeedleToAgenda
import com.example.tasky.authentication.domain.UserCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    dateGenerator: DateGenerator,
    userCache: UserCache,
    private val agendaRepository: AgendaRepository,
) : ViewModel() {

    var state by mutableStateOf(AgendaState())
        private set

    init {
        state = state.copy(
            days = dateGenerator.getWeek(),
            userInitials = GetInitialsUseCase(userCache.getUser()?.fullName),
            selectedMonth = dateGenerator.getMonth()
        )
        val now = LocalDate.now()
        viewModelScope.launch {
            agendaRepository.getAgenda(now).collectLatest {
                state = state.copy(
                    agendaItems = AddNeedleToAgenda(now.toEpochDay(), it.toMutableList())
                )
            }
        }
        viewModelScope.launch {
            val fetchSuccessfully = agendaRepository.fetchAgenda(now)

            state = state.copy(
                errorMessage = if (!fetchSuccessfully) "An error ocurred" else null
            )
        }
    }

    fun onEvent(event: AgendaEvent) {

    }
}