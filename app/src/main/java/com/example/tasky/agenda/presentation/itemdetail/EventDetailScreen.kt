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
import com.example.tasky.agenda.domain.util.toCurrentDate
import com.example.tasky.agenda.domain.util.toHours
import com.example.tasky.agenda.domain.util.toLocalDateTime
import com.example.tasky.agenda.domain.util.toSimplifiedDate
import com.example.tasky.agenda.presentation.itemdetail.components.AttendeeTypeText
import com.example.tasky.agenda.presentation.itemdetail.components.BottomDecorator
import com.example.tasky.agenda.presentation.itemdetail.components.DetailAttendeeList
import com.example.tasky.agenda.presentation.itemdetail.components.DetailDescription
import com.example.tasky.agenda.presentation.itemdetail.components.DetailPhotosContainer
import com.example.tasky.agenda.presentation.itemdetail.components.DetailReminder
import com.example.tasky.agenda.presentation.itemdetail.components.DetailTitle
import com.example.tasky.agenda.presentation.itemdetail.components.DetailTypeIndicator
import com.example.tasky.agenda.presentation.itemdetail.components.DetailVisitorsTitle
import com.example.tasky.agenda.presentation.itemdetail.components.EventActionText
import com.example.tasky.agenda.presentation.itemdetail.components.TimeDatePicker
import com.example.tasky.agenda.presentation.itemdetail.components.VisitorTypeList
import com.example.tasky.agenda.presentation.itemdetail.model.NotificationType
import com.example.tasky.agenda.presentation.itemdetail.viewmodel.EventDetailEvent
import com.example.tasky.agenda.presentation.itemdetail.viewmodel.EventDetailState
import com.example.tasky.core.util.ObserveBoolean
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.LightGreen
import com.vanpra.composematerialdialogs.MaterialDialog
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
                        ) {}
                    }

                    item {
                        DetailDescription(
                            description = state.event.description,
                            isEditMode = state.isEditMode
                        ) {

                        }
                    }

                    item {
                        DetailPhotosContainer {}
                    }

                    item {
                        TimeDatePicker(
                            time = state.event.from.toHours(),
                            timePrefix = stringResource(id = R.string.from),
                            date = state.event.from.toSimplifiedDate(),
                            isEditMode = state.isEditMode,
                            onTimeClicked = {
                                onEvent(EventDetailEvent.ShowTimePicker(isFromTime = true))
                            },
                            onDateClicked = {}
                        )
                    }

                    item {
                        TimeDatePicker(
                            time = state.event.to.toHours(),
                            timePrefix = stringResource(id = R.string.to),
                            date = state.event.to.toSimplifiedDate(),
                            isEditMode = state.isEditMode,
                            onTimeClicked = {
                                onEvent(EventDetailEvent.ShowTimePicker(isFromTime = false))
                            },
                            onDateClicked = {}
                        )
                    }

                    item {
                        DetailReminder(
                            text = stringResource(
                                NotificationType.from(
                                    dateTime = state.event.from.toLocalDateTime(),
                                    notificationTime = state.event.remindAt.toLocalDateTime()
                                ).type
                            ),
                            isEditMode = state.isEditMode
                        ) {

                        }
                    }

                    item {
                        DetailVisitorsTitle(isEditMode = state.isEditMode) {

                        }
                    }

                    item {
                        VisitorTypeList(selectedFilter = state.selectedFilter) { visitorType ->
                            onEvent(EventDetailEvent.OnFilterClicked(visitorType))
                        }
                    }

                    item {
                        AttendeeTypeText(text = R.string.going)
                    }

                    item {
                        DetailAttendeeList(isEditMode = state.isEditMode)
                    }

                    item {
                        AttendeeTypeText(text = R.string.not_going)
                    }

                    item {
                        DetailAttendeeList(isEditMode = state.isEditMode)
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

@Preview
@Composable
fun EventDetailScreenPreview() {
    EventDetailScreen(EventDetailState()) {}
}

@Preview
@Composable
fun EventDetailScreenPreviewWithEdit() {
    EventDetailScreen(EventDetailState(isEditMode = true)) {}
}