package com.example.tasky.agenda.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasky.agenda.presentation.components.DayPill
import com.example.tasky.agenda.presentation.components.MonthButton
import com.example.tasky.agenda.presentation.components.Needle
import com.example.tasky.agenda.presentation.components.ProfileButton
import com.example.tasky.agenda.presentation.model.Day
import com.example.tasky.agenda.presentation.viewmodel.AgendaState
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.TaskyTheme

@Composable
fun AgendaScreen(
    state: AgendaState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Black,
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MonthButton(text = state.selectedMonth) {
                    // TODO on month click
                }

                Spacer(modifier = Modifier.weight(1f))

                ProfileButton(text = state.userInitials) {
                    // TODO profile click
                }
            }

            Scaffold(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 25.dp,
                            topEnd = 25.dp
                        )
                    ),
                containerColor = White
            ) { padding ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 22.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        state.days.forEach { day ->
                            DayPill(day = day)
                        }
                    }
                    Needle()
                }
            }
        }
    }
}

@Preview
@Composable
fun AgendaScreenPreview() {
    TaskyTheme {
        AgendaScreen(
            AgendaState(
                selectedMonth = "MARCH",
                userInitials = "MK",
                days = (1..7).map {
                    Day("S", it.toString(), it == 1)
                }
            )
        )
    }
}