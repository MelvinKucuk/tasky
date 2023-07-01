package com.example.tasky.itemdetail.presentation.viewmodel

sealed class EventDetailEvent {
    object OnCloseClick : EventDetailEvent()
    object CloseClickResolved : EventDetailEvent()
}
