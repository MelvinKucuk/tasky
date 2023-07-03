@file:SuppressLint("NewApi")

package com.example.tasky.agenda.presentation.itemdetail.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.TaskyRoutes
import com.example.tasky.agenda.domain.EventRepository
import com.example.tasky.agenda.domain.util.toLocalDate
import com.example.tasky.agenda.domain.util.toLong
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

            EventDetailEvent.HideTimePicker -> {
                state = state.copy(showTimePicker = false)
            }

            is EventDetailEvent.ShowTimePicker -> {
                state = state.copy(
                    showTimePicker = true,
                    isFrom = event.isFromTime
                )
            }

            is EventDetailEvent.TimeSelected -> {
                state = if (state.isFrom == true) {
                    state.copy(
                        event = state.event.copy(
                            from = event.time.atDate(state.event.from.toLocalDate()).toLong()
                        ),
                        isFrom = null
                    )
                } else {
                    state.copy(
                        event = state.event.copy(
                            to = event.time.atDate(state.event.to.toLocalDate()).toLong()
                        ),
                        isFrom = null
                    )
                }
            }
        }
    }
}