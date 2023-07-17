package com.example.tasky.agenda.presentation.itemdetail.viewmodel

import com.example.tasky.agenda.domain.model.Attendee
import com.example.tasky.agenda.presentation.itemdetail.model.VisitorType
import java.time.LocalDate
import java.time.LocalTime

sealed class EventDetailEvent {
    object OnCloseClick : EventDetailEvent()
    object CloseClickResolved : EventDetailEvent()
    object NavigateEditTitle : EventDetailEvent()
    object NavigateEditDescription : EventDetailEvent()
    object NavigateEditTitleResolved : EventDetailEvent()
    object NavigateEditDescriptionResolved : EventDetailEvent()
    data class ShowTimePicker(val dateTimeSelected: DateTimeSelector) : EventDetailEvent()
    object HideTimePicker : EventDetailEvent()
    data class TimeSelected(val time: LocalTime) : EventDetailEvent()
    data class ShowDatePicker(val dateTimeSelected: DateTimeSelector) : EventDetailEvent()
    object HideDatePicker : EventDetailEvent()
    data class DateSelected(val date: LocalDate) : EventDetailEvent()
    data class OnFilterClicked(val filter: VisitorType) : EventDetailEvent()
    object AddAttendeeClicked : EventDetailEvent()
    data class OnAttendeeDelete(val attendee: Attendee) : EventDetailEvent()
    object AddButtonClicked : EventDetailEvent()
    object AddAttendeeCloseClicked : EventDetailEvent()
    data class EmailChanged(val email: String) : EventDetailEvent()
    data class PhotoSelected(val url: String) : EventDetailEvent()
    data class PhotoClicked(val url: String) : EventDetailEvent()
    object PhotoClickedResolved : EventDetailEvent()
}
