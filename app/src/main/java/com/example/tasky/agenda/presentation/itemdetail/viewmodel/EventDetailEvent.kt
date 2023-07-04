package com.example.tasky.agenda.presentation.itemdetail.viewmodel

import com.example.tasky.agenda.presentation.itemdetail.model.VisitorType
import java.time.LocalDate
import java.time.LocalTime

sealed class EventDetailEvent {
    object OnCloseClick : EventDetailEvent()
    object CloseClickResolved : EventDetailEvent()
    data class ShowTimePicker(val isFrom: Boolean) : EventDetailEvent()
    object HideTimePicker : EventDetailEvent()
    data class TimeSelected(val time: LocalTime) : EventDetailEvent()
    data class ShowDatePicker(val isFrom: Boolean) : EventDetailEvent()
    object HideDatePicker : EventDetailEvent()
    data class DateSelected(val date: LocalDate) : EventDetailEvent()
    data class OnFilterClicked(val filter: VisitorType) : EventDetailEvent()
}
