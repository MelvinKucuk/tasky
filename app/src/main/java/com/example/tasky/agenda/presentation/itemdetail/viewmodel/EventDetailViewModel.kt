package com.example.tasky.agenda.presentation.itemdetail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.TaskyRoutes
import com.example.tasky.agenda.domain.EventRepository
import com.example.tasky.agenda.domain.model.AgendaPhoto
import com.example.tasky.agenda.domain.model.Attendee
import com.example.tasky.agenda.domain.util.toLocalDate
import com.example.tasky.agenda.domain.util.toLocalTime
import com.example.tasky.agenda.domain.util.toLong
import com.example.tasky.agenda.presentation.edit.model.EditType
import com.example.tasky.core.domain.EmailValidator
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
                    event = event,
                    canAddPhoto = event.photos.size < MAX_PHOTOS
                )
            }
        }
    }

    fun setEditedText(savedStateHandle: SavedStateHandle) {
        val editedText = savedStateHandle.get<String>(TaskyRoutes.EditScreen.TEXT)
        val editType = savedStateHandle.get<EditType>(TaskyRoutes.EditScreen.EDIT_TYPE)
        if (editedText == null) {
            return
        }
        if (editType == null) {
            return
        }
        state = when (editType) {
            EditType.Title -> state.copy(
                event = state.event.copy(
                    title = editedText
                )
            )

            EditType.Description -> state.copy(
                event = state.event.copy(
                    description = editedText
                )
            )
        }
    }

    fun deletePhoto(savedStateHandle: SavedStateHandle) {
        val url = savedStateHandle.get<String>(TaskyRoutes.PhotoViewerScreen.IMAGE_URL) ?: return
        val photos = state.event.photos.toMutableList()
        val photoToDelete = photos.first { it.url == url }
        photos.remove(photoToDelete)
        state = state.copy(
            event = state.event.copy(
                photos = photos
            )
        )
        savedStateHandle[TaskyRoutes.PhotoViewerScreen.IMAGE_URL] = null
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

            EventDetailEvent.AddAttendeeClicked ->
                state = state.copy(
                    attendeeDialogState = AttendeeDialogState(
                        show = true
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
                                    isGoing = true
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

            is EventDetailEvent.PhotoSelected -> {
                val photos = state.event.photos.toMutableList()
                photos.add(AgendaPhoto.Local(event.url))
                state = state.copy(
                    event = state.event.copy(
                        photos = photos
                    ),
                    canAddPhoto = photos.size < MAX_PHOTOS
                )
            }

            is EventDetailEvent.PhotoClicked -> {
                state = state.copy(navigatePhotoViewer = event.url)
            }

            EventDetailEvent.PhotoClickedResolved -> {
                state = state.copy(navigatePhotoViewer = null)
            }

            is EventDetailEvent.OnAttendeeDelete -> {
                val attendees = state.event.attendees.toMutableList()
                val attendeeToDelte = attendees.first { it.userId == event.attendee.userId }
                attendees.remove(attendeeToDelte)
                state = state.copy(
                    event = state.event.copy(
                        attendees = attendees
                    )
                )
            }
        }
    }

    companion object {
        const val MAX_PHOTOS = 10
    }
}