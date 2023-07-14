package com.example.tasky.agenda.presentation.itemdetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasky.agenda.domain.model.Attendee

@Composable
fun DetailAttendeeList(
    isEditMode: Boolean,
    attendees: List<Attendee>,
    hostId: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        attendees.forEach { attendee ->
            VisitorRow(
                visitor = attendee,
                isEditMode = isEditMode,
                hostId = hostId
            ) {}
        }
    }

    Spacer(modifier = Modifier.size(45.dp))
}

@Preview
@Composable
fun DetailAttendeeListPreview() {
    DetailAttendeeList(
        isEditMode = false,
        listOf(
            Attendee(
                fullName = "Melvin Alex Kucuk"
            ),
            Attendee(
                fullName = "Juan Perez"
            ),
            Attendee(
                fullName = "Kevin"
            ),
        ),
        hostId = ""
    )
}