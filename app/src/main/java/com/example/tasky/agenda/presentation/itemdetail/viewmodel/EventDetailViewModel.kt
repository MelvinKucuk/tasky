package com.example.tasky.agenda.presentation.itemdetail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.TaskyRoutes
import com.example.tasky.agenda.domain.EventRepository
import com.example.tasky.agenda.domain.model.Attendee
import com.example.tasky.agenda.domain.util.toLocalDate
import com.example.tasky.agenda.domain.util.toLocalTime
import com.example.tasky.agenda.domain.util.toLong
import com.example.tasky.authentication.domain.EmailValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: EventRepository,
    private val validateEmail: EmailValidator
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

            EventDetailEvent.AddAttendeeClicked ->
                state = state.copy(
                    attendeeDialogState = state.attendeeDialogState.copy(
                        show = true,
                        isLoading = false,
                        email = "",
                        showError = false,
                        showSuccess = false
                    )
                )

            EventDetailEvent.AddAttendeeCloseClicked ->
                state = state.copy(
                    attendeeDialogState = state.attendeeDialogState.copy(
                        show = false
                    )
                )

            EventDetailEvent.AddButtonClicked -> {
                state = state.copy(
                    attendeeDialogState = state.attendeeDialogState.copy(
                        isLoading = true
                    )
                )

                viewModelScope.launch {
                    val attendee = repository.getValidUser(state.attendeeDialogState.email)

                    state = if (attendee != null) {
                        state.copy(
                            attendeeDialogState = state.attendeeDialogState.copy(
                                isLoading = false,
                                showSuccess = true
                            ),
                            event = state.event.copy(
                                attendees = state.event.attendees + Attendee(
                                    email = attendee.email,
                                    fullName = attendee.fullName,
                                    userId = attendee.userId,
                                    eventId = state.eventId,
                                    isGoing = true,
                                    remindAt = state.event.remindAt
                                )
                            )
                        )
                    } else {
                        state.copy(
                            attendeeDialogState = state.attendeeDialogState.copy(
                                isLoading = false,
                                showError = true
                            )
                        )
                    }
                }
            }

            is EventDetailEvent.EmailChanged -> {
                val isValid = validateEmail(event.email)
                state = state.copy(
                    attendeeDialogState = state.attendeeDialogState.copy(
                        email = event.email,
                        isValidEmail = isValid,
                        isEnabled = isValid,
                        showSuccess = false,
                        showError = false
                    )
                )
            }
        }
    }
}