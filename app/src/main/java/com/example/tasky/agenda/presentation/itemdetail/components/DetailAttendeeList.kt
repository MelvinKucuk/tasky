package com.example.tasky.agenda.presentation.itemdetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasky.core.domain.model.Attendee

@Composable
fun DetailAttendeeList(
    isEditMode: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        (1..3).forEach { _ ->
            VisitorRow(
                visitor = Attendee(
                    fullName = "Melvin Alex Kucuk"
                ),
                isEditMode = isEditMode
            ) {}
        }
    }

    Spacer(modifier = Modifier.size(45.dp))
}

@Preview
@Composable
fun DetailAttendeeListPreview() {
    DetailAttendeeList(false)
}