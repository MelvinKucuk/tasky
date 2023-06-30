package com.example.tasky.itemdetail.presentation

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
import com.example.tasky.itemdetail.presentation.components.AttendeeTypeText
import com.example.tasky.itemdetail.presentation.components.BottomDecorator
import com.example.tasky.itemdetail.presentation.components.DetailAttendeeList
import com.example.tasky.itemdetail.presentation.components.DetailDescription
import com.example.tasky.itemdetail.presentation.components.DetailPhotosContainer
import com.example.tasky.itemdetail.presentation.components.DetailReminder
import com.example.tasky.itemdetail.presentation.components.DetailTitle
import com.example.tasky.itemdetail.presentation.components.DetailTypeIndicator
import com.example.tasky.itemdetail.presentation.components.DetailVisitorsTitle
import com.example.tasky.itemdetail.presentation.components.EventActionText
import com.example.tasky.itemdetail.presentation.components.TimeDatePicker
import com.example.tasky.itemdetail.presentation.components.VisitorTypeList
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.LightGreen

@Composable
fun EventDetailScreen(
    state: EventDetailState
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = Color.White,
                    contentDescription = stringResource(R.string.close)
                )

                Text(
                    text = "01 MARCH 2022",
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
                            title = "Title of event",
                            isEditMode = state.isEditMode
                        ) {}
                    }

                    item {
                        DetailDescription(
                            description = "This is a description",
                            isEditMode = state.isEditMode
                        ) {

                        }
                    }

                    item {
                        DetailPhotosContainer {}
                    }

                    item {
                        TimeDatePicker(
                            time = "08:00",
                            timePrefix = stringResource(id = R.string.from),
                            date = "Jul 21 2023",
                            isEditMode = state.isEditMode,
                            onTimeClicked = {},
                            onDateClicked = {}
                        )
                    }

                    item {
                        TimeDatePicker(
                            time = "10:00",
                            timePrefix = stringResource(id = R.string.to),
                            date = "Jul 21 2023",
                            isEditMode = state.isEditMode,
                            onTimeClicked = {},
                            onDateClicked = {}
                        )
                    }

                    item {
                        DetailReminder(text = "30 minutes before", isEditMode = state.isEditMode) {

                        }
                    }

                    item {
                        DetailVisitorsTitle(isEditMode = state.isEditMode) {

                        }
                    }

                    item {
                        VisitorTypeList(selectedFilter = state.selectedFilter) {}
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
    EventDetailScreen(EventDetailState())
}

@Preview
@Composable
fun EventDetailScreenPreviewWithEdit() {
    EventDetailScreen(EventDetailState(isEditMode = true))
}