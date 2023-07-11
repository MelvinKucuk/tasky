package com.example.tasky.agenda.presentation.photoviewer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tasky.TaskyRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(
        PhotoViewerState(
            imageUrl = savedStateHandle.get<String>(TaskyRoutes.PhotoViewerScreen.IMAGE_URL) ?: ""
        )
    )
        private set

    fun onEvent(event: PhotoViewerEvent) {
        state = when (event) {
            PhotoViewerEvent.OnBack -> state.copy(navigateBack = true)
            PhotoViewerEvent.OnBackResolved -> state.copy(navigateBack = null)
            PhotoViewerEvent.OnDelete -> state.copy(navigateWithDelete = state.imageUrl)
            PhotoViewerEvent.OnDeleteResolved -> state.copy(navigateWithDelete = null)
        }
    }
}

data class PhotoViewerState(
    val imageUrl: String = "",
    val navigateBack: Boolean? = null,
    val navigateWithDelete: String? = null
)

sealed class PhotoViewerEvent {
    object OnBack : PhotoViewerEvent()
    object OnBackResolved : PhotoViewerEvent()
    object OnDelete : PhotoViewerEvent()
    object OnDeleteResolved : PhotoViewerEvent()
}