package com.example.tasky.agenda.presentation.itemdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.domain.model.Attendee
import com.example.tasky.agenda.domain.util.toCurrentDate
import com.example.tasky.agenda.domain.util.toHours
import com.example.tasky.agenda.domain.util.toSimplifiedDate
import com.example.tasky.agenda.presentation.itemdetail.components.AddAttendeeDialog
import com.example.tasky.agenda.presentation.itemdetail.components.AttendeeTypeText
import com.example.tasky.agenda.presentation.itemdetail.components.BottomDecorator
import com.example.tasky.agenda.presentation.itemdetail.components.DetailAttendeeList
import com.example.tasky.agenda.presentation.itemdetail.components.DetailDescription
import com.example.tasky.agenda.presentation.itemdetail.components.DetailReminder
import com.example.tasky.agenda.presentation.itemdetail.components.DetailTitle
import com.example.tasky.agenda.presentation.itemdetail.components.DetailTypeIndicator
import com.example.tasky.agenda.presentation.itemdetail.components.DetailVisitorsTitle
import com.example.tasky.agenda.presentation.itemdetail.components.EventActionText
import com.example.tasky.agenda.presentation.itemdetail.components.TimeDatePicker
import com.example.tasky.agenda.presentation.itemdetail.components.VisitorTypeList
import com.example.tasky.agenda.presentation.itemdetail.model.VisitorType
import com.example.tasky.agenda.presentation.itemdetail.viewmodel.DateTimeSelector
import com.example.tasky.agenda.presentation.itemdetail.viewmodel.EventDetailEvent
import com.example.tasky.agenda.presentation.itemdetail.viewmodel.EventDetailState
import com.example.tasky.core.util.ObserveBoolean
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.LightGreen
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun EventDetailScreen(
    state: EventDetailState,
    onEvent: (EventDetailEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Black,
    ) {

        if (state.attendeeDialogState.show) {
            AddAttendeeDialog(
                state = state.attendeeDialogState,
                textValueChanged = { mail ->
                    onEvent(EventDetailEvent.EmailChanged(mail))
                },
                onCloseClick = {
                    onEvent(EventDetailEvent.AddAttendeeCloseClicked)
                },
                onAddClick = {
                    onEvent(EventDetailEvent.AddButtonClicked)
                }
            )
        }

        val dialogState = rememberMaterialDialogState()
        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton(stringResource(R.string.ok))
                negativeButton(stringResource(R.string.cancel)) {
                    onEvent(EventDetailEvent.HideTimePicker)
                }
            },
            onCloseRequest = { dialog ->
                onEvent(EventDetailEvent.HideTimePicker)
                dialog.hide()
            }
        ) {
            timepicker { time ->
                onEvent(EventDetailEvent.TimeSelected(time))
            }
        }

        ObserveBoolean(observer = state.showTimePicker) {
            dialogState.show()
        }

        val dateDialogState = rememberMaterialDialogState()
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(stringResource(R.string.ok))
                negativeButton(stringResource(R.string.cancel)) {
                    onEvent(EventDetailEvent.HideDatePicker)
                }
            },
            onCloseRequest = { dialog ->
                onEvent(EventDetailEvent.HideDatePicker)
                dialog.hide()
            }
        ) {
            datepicker { date ->
                onEvent(EventDetailEvent.DateSelected(date))
            }
        }

        ObserveBoolean(observer = state.showDatePicker) {
            dateDialogState.show()
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier.clickable { onEvent(EventDetailEvent.OnCloseClick) },
                    imageVector = Icons.Filled.Close,
                    tint = Color.White,
                    contentDescription = stringResource(R.string.close)
                )

                Text(
                    text = state.event.from.toCurrentDate(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Icon(
                    imageVector = Icons.Filled.Edit,
                    tint = Color.White,
                    contentDescription = stringResource(R.string.edit)
                )
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
                containerColor = Color.White
            ) { padding ->
                LazyColumn(modifier = Modifier.padding(padding)) {
                    item {
                        DetailTypeIndicator(
                            text = R.string.event,
                            color = LightGreen
                        )
                    }
                    item {
                        DetailTitle(
                            title = state.event.title,
                            isEditMode = state.isEditMode
                        ) {
                            onEvent(EventDetailEvent.NavigateEditTitle)
                        }
                    }

                    item {
                        DetailDescription(
                            description = state.event.description,
                            isEditMode = state.isEditMode
                        ) {
                            onEvent(EventDetailEvent.NavigateEditDescription)
                        }
                    }

                    item {
                        /*DetailPhotosContainer(
                            photos = state.event.photos,
                            canAddPhoto = state.canAddPhoto,
                            onPhotoSelected = { url ->
                                onEvent(EventDetailEvent.PhotoSelected(url))
                            },
                            onPhotoClick = { url ->
                                onEvent(EventDetailEvent.PhotoClicked(url))
                            }
                        )*/
                    }

                    item {
                        TimeDatePicker(
                            time = state.event.from.toHours(),
                            timePrefix = stringResource(id = R.string.from),
                            date = state.event.from.toSimplifiedDate(),
                            isEditMode = state.isEditMode,
                            onTimeClicked = {
                                onEvent(
                                    EventDetailEvent
                                        .ShowTimePicker(dateTimeSelected = DateTimeSelector.From)
                                )
                            },
                            onDateClicked = {
                                onEvent(
                                    EventDetailEvent
                                        .ShowDatePicker(dateTimeSelected = DateTimeSelector.From)
                                )
                            }
                        )
                    }

                    item {
                        TimeDatePicker(
                            time = state.event.to.toHours(),
                            timePrefix = stringResource(id = R.string.to),
                            date = state.event.to.toSimplifiedDate(),
                            isEditMode = state.isEditMode,
                            onTimeClicked = {
                                onEvent(
                                    EventDetailEvent
                                        .ShowTimePicker(dateTimeSelected = DateTimeSelector.To)
                                )
                            },
                            onDateClicked = {
                                onEvent(
                                    EventDetailEvent
                                        .ShowDatePicker(dateTimeSelected = DateTimeSelector.To)
                                )
                            }
                        )
                    }

                    item {
                        DetailReminder(
                            text = stringResource(state.remindAt),
                            isEditMode = state.isEditMode
                        ) { reminder ->
                            onEvent(EventDetailEvent.ReminderChanged(reminder))
                        }
                    }

                    item {
                        DetailVisitorsTitle(isEditMode = state.isEditMode) {
                            onEvent(EventDetailEvent.AddAttendeeClicked)
                        }
                    }

                    if (state.attendeesGoing.isNotEmpty() || state.attendeesNotGoing.isNotEmpty()) {
                        item {
                            VisitorTypeList(selectedFilter = state.selectedFilter) { visitorType ->
                                onEvent(EventDetailEvent.OnFilterClicked(visitorType))
                            }
                        }
                    }

                    when (state.selectedFilter) {
                        VisitorType.ALL -> {
                            item { AttendeesGoing(state, onEvent) }
                            item { AttendeesNotGoing(state, onEvent) }
                        }

                        VisitorType.GOING -> {
                            item { AttendeesGoing(state, onEvent) }
                        }

                        VisitorType.NOT_GOING -> {
                            item { AttendeesNotGoing(state, onEvent) }
                        }
                    }

                    item {
                        EventActionText(R.string.delete_event) {}
                    }

                    item {
                        BottomDecorator()
                    }
                }
            }
        }
    }
}

@Composable
private fun AttendeesNotGoing(state: EventDetailState, onEvent: (EventDetailEvent) -> Unit) {
    if (state.attendeesNotGoing.isNotEmpty()) {
        AttendeeTypeText(text = R.string.not_going)

        DetailAttendeeList(
            isEditMode = state.isEditMode,
            attendees = state.attendeesNotGoing,
            hostId = state.event.host
        ) {
            onEvent(EventDetailEvent.OnAttendeeDelete(it))
        }
    }
}

@Composable
private fun AttendeesGoing(state: EventDetailState, onEvent: (EventDetailEvent) -> Unit) {
    if (state.attendeesGoing.isNotEmpty()) {
        AttendeeTypeText(text = R.string.going)

        DetailAttendeeList(
            isEditMode = state.isEditMode,
            attendees = state.attendeesGoing,
            hostId = state.event.host
        ) {
            onEvent(EventDetailEvent.OnAttendeeDelete(it))
        }
    }
}

@Preview
@Composable
fun EventDetailScreenPreview() {
    EventDetailScreen(
        EventDetailState(
            event = AgendaItem.Event(
                title = "This is a title",
                description = "This is a description",
                attendees = listOf(
                    Attendee(
                        fullName = "Melvin Alex Kucuk",
                        isGoing = true,
                        isUserEventCreator = true
                    ),
                    Attendee(
                        fullName = "Juan Perez",
                        isGoing = false
                    )
                )
            )
        )
    ) {}
}

@Preview
@Composable
fun EventDetailScreenPreviewWithEdit() {
    EventDetailScreen(
        EventDetailState(
            event = AgendaItem.Event(
                title = "This is a title",
                description = "This is a description",
                attendees = listOf(
                    Attendee(
                        fullName = "Melvin Alex Kucuk",
                        isGoing = true,
                        isUserEventCreator = true
                    ),
                    Attendee(
                        fullName = "Melvin Alex Kucuk",
                        isGoing = false
                    )
                )
            ),
            isEditMode = true
        )
    ) {}
}