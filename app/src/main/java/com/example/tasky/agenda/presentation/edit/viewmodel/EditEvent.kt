package com.example.tasky.agenda.presentation.edit.viewmodel

sealed class EditEvent {
    data class OnTextChanged(val text: String) : EditEvent()
    object OnBackClicked : EditEvent()
    object OnBackResolved : EditEvent()
    object OnSaveClicked : EditEvent()
    object OnSaveResolved : EditEvent()
}
