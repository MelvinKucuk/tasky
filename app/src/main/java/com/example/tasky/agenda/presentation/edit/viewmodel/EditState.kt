package com.example.tasky.agenda.presentation.edit.viewmodel

import com.example.tasky.agenda.presentation.edit.model.EditType

data class EditState(
    val editType: EditType = EditType.Title,
    val text: String = "",
    val navigateBack: Boolean? = null,
    val navigateWithResult: String? = null
)
