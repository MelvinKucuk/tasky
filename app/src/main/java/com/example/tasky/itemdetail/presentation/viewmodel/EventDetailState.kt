package com.example.tasky.itemdetail.presentation.viewmodel

import com.example.tasky.core.domain.model.AgendaItem
import com.example.tasky.itemdetail.presentation.model.VisitorType

data class EventDetailState(
    val eventId: String = "",
    val event: AgendaItem.Event = AgendaItem.Event(),
    val isEditMode: Boolean = false,
    val selectedFilter: VisitorType = VisitorType.ALL
)
