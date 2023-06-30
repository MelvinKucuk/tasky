package com.example.tasky.itemdetail.presentation

import com.example.tasky.itemdetail.presentation.model.VisitorType

data class EventDetailState(
    val isEditMode: Boolean = false,
    val selectedFilter: VisitorType = VisitorType.ALL
)
