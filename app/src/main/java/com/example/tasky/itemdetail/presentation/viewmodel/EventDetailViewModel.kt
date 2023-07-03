package com.example.tasky.itemdetail.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.TaskyRoutes
import com.example.tasky.itemdetail.domain.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: EventRepository
) : ViewModel() {

    var state by mutableStateOf(EventDetailState())
        private set

    init {
        val eventId = savedStateHandle.get<String>(TaskyRoutes.EventDetailScreen.EVENT_ID)
        if (eventId != null) {
            state = state.copy(eventId = eventId)
            viewModelScope.launch {
                val event = repository.getEventById(state.eventId)

                state = state.copy(
                    event = event
                )
            }
        }
    }

    fun onEvent(event: EventDetailEvent) {
        when (event) {
            EventDetailEvent.OnCloseClick -> {
                state = state.copy(navigateBack = true)
            }

            EventDetailEvent.CloseClickResolved -> {
                state = state.copy(navigateBack = false)
            }

            is EventDetailEvent.OnFilterClicked -> {
                state = state.copy(selectedFilter = event.filter)
            }
        }
    }
}