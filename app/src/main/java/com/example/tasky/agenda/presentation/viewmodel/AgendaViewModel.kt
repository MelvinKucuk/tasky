package com.example.tasky.agenda.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.agenda.domain.AgendaRepository
import com.example.tasky.agenda.domain.DateGenerator
import com.example.tasky.agenda.domain.GetInitialsUseCase
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.domain.model.Day
import com.example.tasky.authentication.domain.UserCache
import com.example.tasky.core.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    dateGenerator: DateGenerator,
    getInitials: GetInitialsUseCase,
    userCache: UserCache,
    private val agendaRepository: AgendaRepository,
) : ViewModel() {

    var state by mutableStateOf(AgendaState())
        private set

    init {
        state = state.copy(
            days = dateGenerator.getWeek(),
            userInitials = getInitials(userCache.getUser()?.fullName),
            selectedMonth = dateGenerator.getMonth()
        )
        viewModelScope.launch {
            val result = agendaRepository.getAgenda()

            if (result is Resource.Success) {
                state = state.copy(
                    agendaItems = result.data
                )
            }
        }
    }
}

data class AgendaState(
    val selectedMonth: String = "",
    val userInitials: String = "",
    val days: List<Day> = listOf(),
    val agendaItems: List<AgendaItem> = listOf()
)