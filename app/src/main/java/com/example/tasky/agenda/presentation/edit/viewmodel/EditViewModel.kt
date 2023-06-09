package com.example.tasky.agenda.presentation.edit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tasky.TaskyRoutes
import com.example.tasky.agenda.presentation.edit.model.EditType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(
        EditState(
            text = savedStateHandle.get<String>(TaskyRoutes.EditScreen.TEXT) ?: "",
            editType = savedStateHandle.get<EditType>(TaskyRoutes.EditScreen.EDIT_TYPE)
                ?: EditType.Title
        )
    )
        private set

    fun onEvent(event: EditEvent) {
        state = when (event) {
            is EditEvent.OnTextChanged -> {
                state.copy(text = event.text)
            }

            EditEvent.OnBackClicked -> {
                state.copy(navigateBack = true)
            }

            EditEvent.OnBackResolved -> {
                state.copy(navigateBack = null)
            }

            is EditEvent.OnSaveClicked -> {
                state.copy(navigateWithResult = state.text)
            }

            EditEvent.OnSaveResolved -> {
                state.copy(navigateWithResult = null)
            }
        }
    }
}