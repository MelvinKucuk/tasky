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
import com.example.tasky.agenda.domain.util.toLocalTime
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

    fun setEditedText(savedStateHandle: SavedStateHandle) {
        val editedText = savedStateHandle.get<String>(TaskyRoutes.EditScreen.TEXT)
        val isTitle = savedStateHandle.get<Boolean>(TaskyRoutes.EditScreen.IS_TITLE)
        if (editedText == null) {
            return
        }
        if (isTitle == null) {
            return
        }
        state = if (isTitle) {
            state.copy(
                event = state.event.copy(
                    title = editedText
                )
            )
        } else {
            state.copy(
                event = state.event.copy(
                    description = editedText
                )
            )
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
                    dateTimeSelected = event.dateTimeSelected
                )
            }

            is EventDetailEvent.TimeSelected -> {
                state = when (state.dateTimeSelected) {
                    DateTimeSelector.From -> {
                        state.copy(
                            event = state.event.copy(
                                from = event.time.atDate(state.event.from.toLocalDate()).toLong()
                            )
                        )
                    }

                    DateTimeSelector.To -> {
                        state.copy(
                            event = state.event.copy(
                                to = event.time.atDate(state.event.to.toLocalDate()).toLong()
                            )
                        )
                    }

                    else -> state
                }
                state = state.copy(
                    dateTimeSelected = null,
                    showTimePicker = false
                )
            }

            EventDetailEvent.HideDatePicker -> state = state.copy(showDatePicker = false)
            is EventDetailEvent.ShowDatePicker -> state = state.copy(
                showDatePicker = true,
                dateTimeSelected = event.dateTimeSelected
            )

            is EventDetailEvent.DateSelected -> {
                state = when (state.dateTimeSelected) {
                    DateTimeSelector.From -> {
                        state.copy(
                            event = state.event.copy(
                                from = event.date.atTime(state.event.from.toLocalTime()).toLong()
                            )
                        )
                    }

                    DateTimeSelector.To -> {
                        state.copy(
                            event = state.event.copy(
                                to = event.date.atTime(state.event.to.toLocalTime()).toLong()
                            )
                        )
                    }

                    else -> state
                }
                state = state.copy(
                    dateTimeSelected = null,
                    showDatePicker = false
                )
            }

            EventDetailEvent.NavigateEditDescriptionResolved ->
                state = state.copy(navigateEditDescription = null)

            EventDetailEvent.NavigateEditTitleResolved ->
                state = state.copy(navigateEditTitle = null)

            EventDetailEvent.NavigateEditDescription ->
                state = state.copy(navigateEditDescription = state.event.description)

            EventDetailEvent.NavigateEditTitle ->
                state = state.copy(navigateEditTitle = state.event.title)
        }
    }
}