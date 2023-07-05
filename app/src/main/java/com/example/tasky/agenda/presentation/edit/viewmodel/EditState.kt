package com.example.tasky.agenda.presentation.edit.viewmodel

data class EditState(
    val isTitle: Boolean = false,
    val text: String = "",
    val navigateBack: Boolean? = null,
    val navigateWithResult: String? = null
)
