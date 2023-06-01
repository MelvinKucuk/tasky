package com.example.tasky.agenda.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tasky.agenda.presentation.model.Day
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(AgendaState())
        private set
}

data class AgendaState(
    val selectedMonth: String = "",
    val userInitials: String = "",
    val days: List<Day> = listOf()
)