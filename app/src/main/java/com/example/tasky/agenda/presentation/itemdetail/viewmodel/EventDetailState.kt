package com.example.tasky.agenda.presentation.itemdetail.viewmodel

import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.presentation.itemdetail.model.VisitorType

data class EventDetailState(
    val eventId: String = "",
    val event: AgendaItem.Event = AgendaItem.Event(),
    val isEditMode: Boolean = true,
    val navigateBack: Boolean = false,
    val showTimePicker: Boolean = false,
    val showDatePicker: Boolean = false,
    val navigateEditTitle: String? = null,
    val navigateEditDescription: String? = null,
    val dateTimeSelected: DateTimeSelector? = null,
    val canAddPhoto: Boolean = true,
    val attendeeDialogState: AttendeeDialogState = AttendeeDialogState(),
    val selectedFilter: VisitorType = VisitorType.ALL,
) {
    val attendeesGoing get() = event.attendees.filter { it.isGoing == true }
    val attendeesNotGoing get() = event.attendees.filter { it.isGoing == false }
}

data class AttendeeDialogState(
    val email: String = "",
    val isValidEmail: Boolean = false,
    val isEnabled: Boolean = false,
    val show: Boolean = false,
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val showSuccess: Boolean = false,
)

sealed class DateTimeSelector {
    object From : DateTimeSelector()
    object To : DateTimeSelector()
}
