package com.example.tasky.agenda.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.agenda.presentation.components.AgendaItem
import com.example.tasky.agenda.presentation.components.DayPill
import com.example.tasky.agenda.presentation.components.MonthButton
import com.example.tasky.agenda.presentation.components.Needle
import com.example.tasky.agenda.presentation.components.ProfileButton
import com.example.tasky.agenda.presentation.model.Agenda
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
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
                        Text(
                            modifier = Modifier
                                .padding(vertical = 20.dp, horizontal = 14.dp)
                                .fillMaxWidth(),
                            text = stringResource(R.string.today),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        LazyColumn(contentPadding = PaddingValues(bottom = 12.dp)) {
                            itemsIndexed(state.agendaItems) { index, item ->
                                when (item) {
                                    is Agenda.AgendaUI -> {
                                        AgendaItem(
                                            item,
                                            onDoneClicked = {},
                                            onMoreOptionsClicked = {}
                                        )
                                        val isLastIndex = state.agendaItems.lastIndex == index
                                        val shouldShowSpacer = when {
                                            isLastIndex -> false
                                            !isLastIndex && state.agendaItems[index + 1] is Agenda.Needle -> false
                                            else -> true
                                        }

                                        if (shouldShowSpacer) {
                                            Spacer(modifier = Modifier.size(15.dp))
                                        }
                                    }

                                    is Agenda.Needle -> Needle()
                                }
                            }
                        }
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .padding(bottom = 33.dp, end = 8.dp),
                        onClick = {
                            //OnClick Method
                        },
                        containerColor = Black,
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add FAB",
                            tint = White,
                        )
                    }
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
                },
                listOf(
                    Agenda.AgendaUI(
                        title = "Project X",
                        description = "Just work",
                        date = "Mar 5, 10:00",
                        isDone = true
                    ),
                    Agenda.Needle,
                    Agenda.AgendaUI(
                        title = "Project X",
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam tempus leo et dolor ultrices, id vestibulum lectus lobortis. Nulla hendrerit ex vitae dui finibus porta. Vestibulum sit amet feugiat justo, fermentum finibus elit. Duis quis molestie lectus, at dapibus magna. Maecenas sagittis justo quis leo imperdiet, commodo cursus lacus aliquam.",
                        date = "Mar 5, 10:00",
                        isDone = false
                    ),
                    Agenda.AgendaUI(
                        title = "Project X",
                        description = "Just work",
                        date = "Mar 5, 10:00",
                        isDone = false
                    )
                )
            )
        )
    }
}