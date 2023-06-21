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
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.presentation.AgendaItemEvent
import com.example.tasky.agenda.presentation.util.AddNeedleToAgenda
import com.example.tasky.authentication.domain.UserCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    dateGenerator: DateGenerator,
    userCache: UserCache,
    private val agendaRepository: AgendaRepository,
) : ViewModel() {

    private val selectedDate: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())

    var state by mutableStateOf(AgendaState())
        private set

    init {
        state = state.copy(
            userInitials = GetInitialsUseCase(userCache.getUser()?.fullName),
        )
        viewModelScope.launch {
            selectedDate.collect {
                getAgendaForDate(it, shouldFetch = true)
                state = state.copy(
                    days = dateGenerator.getWeek(it),
                    selectedMonth = dateGenerator.getMonth(it)
                )
            }
        }
    }

    private fun getAgendaForDate(date: LocalDate, shouldFetch: Boolean = false) {
        viewModelScope.launch {
            agendaRepository.getAgenda(date).collect {
                state = state.copy(
                    agendaItems = AddNeedleToAgenda(date.toEpochDay(), it.toMutableList())
                )
            }
        }
        if (shouldFetch) {
            viewModelScope.launch {
                val fetchSuccessfully = agendaRepository.fetchAgenda(date)

                state = state.copy(
                    errorMessage = if (!fetchSuccessfully) "An error ocurred" else null
                )
            }
        }
    }

    fun onEvent(event: AgendaEvent) {
        when (event) {
            is AgendaEvent.MonthClick -> {
                state = state.copy(showCalendar = true)
            }

            is AgendaEvent.MonthDismiss -> {
                state = state.copy(showCalendar = false)
            }

            is AgendaEvent.DateSelected -> viewModelScope.launch {
                selectedDate.emit(event.date)
                state = state.copy(showCalendar = false)
            }

            is AgendaEvent.DayClicked -> viewModelScope.launch {
                selectedDate.emit(
                    selectedDate.value.withDayOfMonth(event.numberOfDay)
                )
            }

            AgendaEvent.FabClicked -> {
                state = state.copy(showFab = true)
            }

            AgendaEvent.FabDismiss -> {
                state = state.copy(showFab = false)
            }

            AgendaEvent.Logout -> {
                state = state.copy(logoutClicked = true)
            }

            AgendaEvent.LogoutHandled -> {
                state = state.copy(logoutClicked = false)
            }

            AgendaEvent.ErrorHandled -> {
                state = state.copy(errorMessage = null)
            }

            is AgendaEvent.NewItem -> {

            }

            is AgendaEvent.OnAgendaItemEvent -> {
                onAgendaItemEvent(
                    event = event.event,
                    agendaItem = event.agendaItem
                )
            }

            AgendaEvent.ProfileClick -> {
                state = state.copy(showProfileMenu = true)
            }

            AgendaEvent.ProfileMenuDismiss -> {
                state = state.copy(showProfileMenu = false)
            }
        }
    }

    private fun onAgendaItemEvent(event: AgendaItemEvent, agendaItem: AgendaItem) {
        when (event) {
            is AgendaItemEvent.DoneClick -> {
                (agendaItem as? AgendaItem.Task)?.let {
                    viewModelScope.launch {
                        agendaRepository.updateTaskStatus(it.id, !it.isDone)
                    }
                }
            }

            is AgendaItemEvent.MenuClick -> {

            }
        }
    }
}