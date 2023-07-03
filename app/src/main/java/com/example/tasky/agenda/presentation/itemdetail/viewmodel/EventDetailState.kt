package com.example.tasky.agenda.presentation.itemdetail.viewmodel

import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.presentation.itemdetail.model.VisitorType

data class EventDetailState(
    val eventId: String = "",
    val event: AgendaItem.Event = AgendaItem.Event(),
    val isEditMode: Boolean = false,
    val navigateBack: Boolean = false,
    val selectedFilter: VisitorType = VisitorType.ALL
)
