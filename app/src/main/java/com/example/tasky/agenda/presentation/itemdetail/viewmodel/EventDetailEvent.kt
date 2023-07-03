package com.example.tasky.agenda.presentation.itemdetail.viewmodel

import com.example.tasky.agenda.presentation.itemdetail.model.VisitorType

sealed class EventDetailEvent {
    object OnCloseClick : EventDetailEvent()
    object CloseClickResolved : EventDetailEvent()
    data class OnFilterClicked(val filter: VisitorType) : EventDetailEvent()
}
