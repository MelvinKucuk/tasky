package com.example.tasky.itemdetail.presentation.viewmodel

import com.example.tasky.itemdetail.presentation.model.VisitorType

sealed class EventDetailEvent {
    object OnCloseClick : EventDetailEvent()
    object CloseClickResolved : EventDetailEvent()
    data class OnFilterClicked(val filter: VisitorType) : EventDetailEvent()
}
