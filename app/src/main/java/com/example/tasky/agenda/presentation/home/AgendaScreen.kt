package com.example.tasky.agenda.presentation.home

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.domain.model.Day
import com.example.tasky.agenda.presentation.home.components.DayPill
import com.example.tasky.agenda.presentation.home.components.EventItem
import com.example.tasky.agenda.presentation.home.components.MonthButton
import com.example.tasky.agenda.presentation.home.components.Needle
import com.example.tasky.agenda.presentation.home.components.ProfileButton
import com.example.tasky.agenda.presentation.home.components.ReminderItem
import com.example.tasky.agenda.presentation.home.components.TaskItem
import com.example.tasky.agenda.presentation.home.components.TaskyDropdownMenu
import com.example.tasky.agenda.presentation.home.viewmodel.AgendaEvent
import com.example.tasky.agenda.presentation.home.viewmodel.AgendaState
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.TaskyTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun AgendaScreen(
    state: AgendaState,
    onEvent: (AgendaEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Black,
    ) {

        val dialogState = rememberMaterialDialogState()
        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton(stringResource(R.string.ok))
                negativeButton(stringResource(R.string.cancel)) {
                    onEvent(AgendaEvent.MonthDismiss)
                }
            },
            onCloseRequest = { dialog ->
                onEvent(AgendaEvent.MonthDismiss)
                dialog.hide()
            }
        ) {
            datepicker { date ->
                onEvent(AgendaEvent.DateSelected(date))
            }
        }

        LaunchedEffect(key1 = state.showCalendar) {
            if (state.showCalendar)
                dialogState.show()
        }

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
                    onEvent(AgendaEvent.MonthClick)
                }

                Spacer(modifier = Modifier.weight(1f))

                Box {
                    ProfileButton(text = state.userInitials) {
                        onEvent(AgendaEvent.ProfileClick)
                    }
                    TaskyDropdownMenu(
                        showDropdown = state.showProfileMenu,
                        items = state.profileMenu,
                        onClick = {
                            onEvent(AgendaEvent.Logout)
                        },
                        onDismiss = {
                            onEvent(AgendaEvent.ProfileMenuDismiss)
                        }
                    )
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
                                DayPill(day = day) { numberOfDay ->
                                    onEvent(AgendaEvent.DayClicked(numberOfDay))
                                }
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
                                    is AgendaItem.Event -> {
                                        EventItem(
                                            event = item,
                                            menuItems = state.agendaItemMenu,
                                            onAgendaItemEvent = { event ->
                                                onEvent(
                                                    AgendaEvent.OnAgendaItemEvent(
                                                        event = event,
                                                        agendaItem = item
                                                    )
                                                )
                                            }
                                        )
                                        AddSpacer(state, index)
                                    }

                                    is AgendaItem.Reminder -> {
                                        ReminderItem(
                                            reminder = item,
                                            menuItems = state.agendaItemMenu,
                                            onAgendaEvent = { event ->
                                                onEvent(
                                                    AgendaEvent.OnAgendaItemEvent(
                                                        event = event,
                                                        agendaItem = item
                                                    )
                                                )
                                            }
                                        )
                                        AddSpacer(state, index)
                                    }

                                    is AgendaItem.Task -> {
                                        TaskItem(
                                            task = item,
                                            menuItems = state.agendaItemMenu,
                                            onAgendaEvent = { event ->
                                                onEvent(
                                                    AgendaEvent.OnAgendaItemEvent(
                                                        event = event,
                                                        agendaItem = item
                                                    )
                                                )
                                            }
                                        )
                                        AddSpacer(state, index)
                                    }

                                    is AgendaItem.Needle -> Needle()
                                }
                            }
                        }
                    }
                    Box {
                        FloatingActionButton(
                            modifier = Modifier
                                .padding(bottom = 33.dp, end = 8.dp),
                            onClick = {
                                onEvent(AgendaEvent.FabClicked)
                            },
                            containerColor = Black,
                            shape = RoundedCornerShape(16.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = stringResource(R.string.add_fab),
                                tint = White,
                            )
                        }
                        TaskyDropdownMenu(
                            showDropdown = state.showFab,
                            items = state.fabMenu,
                            offset = DpOffset(8.dp, 0.dp),
                            onClick = { item ->
                                onEvent(AgendaEvent.NewItem(item))
                            },
                            onDismiss = {
                                onEvent(AgendaEvent.FabDismiss)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddSpacer(
    state: AgendaState,
    index: Int
) {
    val isLastIndex = state.agendaItems.lastIndex == index
    val shouldShowSpacer = when {
        isLastIndex -> false
        !isLastIndex && state.agendaItems[index + 1] is AgendaItem.Needle -> false
        else -> true
    }

    if (shouldShowSpacer) {
        Spacer(modifier = Modifier.size(15.dp))
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
                    AgendaItem.Event(
                        title = "Project X",
                        description = "Just work",
                        from = 1686697568646,
                        to = 1686697868646
                    ),
                    AgendaItem.Needle,
                    AgendaItem.Task(
                        title = "Project X",
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam tempus leo et dolor ultrices, id vestibulum lectus lobortis. Nulla hendrerit ex vitae dui finibus porta. Vestibulum sit amet feugiat justo, fermentum finibus elit. Duis quis molestie lectus, at dapibus magna. Maecenas sagittis justo quis leo imperdiet, commodo cursus lacus aliquam.",
                        time = 1686697568646,
                        isDone = false
                    ),
                    AgendaItem.Reminder(
                        title = "Project X",
                        description = "Just work",
                        time = 1686697568646
                    )
                )
            )
        ) {}
    }
}