@file:SuppressLint("NewApi")

package com.example.tasky.agenda.presentation.home.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.agenda.domain.AgendaRepository
import com.example.tasky.agenda.domain.DateGenerator
import com.example.tasky.agenda.domain.getInitials
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.domain.util.toCurrentTimeMilli
import com.example.tasky.agenda.presentation.home.AgendaItemEvent
import com.example.tasky.agenda.presentation.home.MenuItem
import com.example.tasky.agenda.presentation.home.util.addNeedleToAgenda
import com.example.tasky.authentication.domain.UserCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    dateGenerator: DateGenerator,
    userCache: UserCache,
    private val agendaRepository: AgendaRepository,
) : ViewModel() {

    private val selectedDate = MutableStateFlow(LocalDate.now())

    private var dataBaseJob: Job? = null

    var state by mutableStateOf(AgendaState())
        private set

    init {
        state = state.copy(
            userInitials = getInitials(userCache.getUser()?.fullName),
        )
        viewModelScope.launch {
            selectedDate.collectLatest {
                getAgendaForDate(it, shouldFetch = true)
                state = state.copy(
                    days = dateGenerator.getWeek(it),
                    selectedMonth = dateGenerator.getMonth(it)
                )
            }
        }
    }

    private fun getAgendaForDate(date: LocalDate, shouldFetch: Boolean = false) {
        dataBaseJob?.cancel()
        dataBaseJob = viewModelScope.launch {
            agendaRepository.getAgenda(date).distinctUntilChanged().collectLatest {
                state = state.copy(
                    agendaItems = addNeedleToAgenda(date.toEpochDay(), it.toMutableList())
                )
            }
        }
        if (shouldFetch) {
            viewModelScope.launch {
                val fetchSuccessfully = agendaRepository.fetchAgenda(date)

                state = state.copy(
                    errorMessage = if (fetchSuccessfully) null else "An error ocurred"
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
                state = state.copy(isLoggedOut = true)
            }

            AgendaEvent.LogoutHandled -> {
                state = state.copy(isLoggedOut = false)
            }

            AgendaEvent.ErrorHandled -> {
                state = state.copy(errorMessage = null)
            }

            AgendaEvent.EventNavigationHandled -> {
                state = state.copy(navigateToEventDetail = null)
            }

            is AgendaEvent.NewItem -> {
                state = state.copy(navigateToNewEvent = selectedDate.value.toCurrentTimeMilli())
            }

            AgendaEvent.NewItemHandled -> {
                state = state.copy(navigateToNewEvent = null)
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
                when (event.menu) {
                    MenuItem.OPEN -> {
                        when (agendaItem) {
                            is AgendaItem.Event -> {
                                state = state.copy(
                                    navigateToEventDetail = agendaItem.id
                                )
                            }

                            is AgendaItem.Reminder -> {}
                            is AgendaItem.Task -> {}
                            else -> {}
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}