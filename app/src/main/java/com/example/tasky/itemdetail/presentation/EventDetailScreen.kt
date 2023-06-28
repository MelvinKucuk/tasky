package com.example.tasky.itemdetail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.agenda.domain.model.Attendee
import com.example.tasky.itemdetail.domain.VisitorType
import com.example.tasky.itemdetail.presentation.components.TimeDatePicker
import com.example.tasky.itemdetail.presentation.components.VisitorRow
import com.example.tasky.itemdetail.presentation.components.VisitorTypePill
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.DarkGray
import com.example.tasky.ui.theme.Gray
import com.example.tasky.ui.theme.Light
import com.example.tasky.ui.theme.Light2
import com.example.tasky.ui.theme.LightGray2
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
                        Row(
                            modifier = Modifier
                                .height(80.dp)
                                .padding(start = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(LightGreen)
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            Text(
                                text = stringResource(id = R.string.event),
                                color = DarkGray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.padding(start = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Outlined.Circle,
                                contentDescription = null,
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            Text(
                                text = "Title of event",
                                color = Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp
                            )

                            if (state.isEditMode) {
                                Spacer(modifier = Modifier.weight(1f))

                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Filled.ChevronRight,
                                    tint = Black,
                                    contentDescription = stringResource(R.string.edit)
                                )

                                Spacer(modifier = Modifier.size(16.dp))
                            }
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 20.dp),
                            color = Light
                        )
                    }

                    item {
                        Row {
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 16.dp),
                                text = "This is the description",
                                color = Black,
                                fontSize = 16.sp
                            )

                            if (state.isEditMode) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Filled.ChevronRight,
                                    tint = Black,
                                    contentDescription = stringResource(R.string.edit)
                                )

                                Spacer(modifier = Modifier.size(16.dp))
                            }

                        }
                        Spacer(modifier = Modifier.size(30.dp))
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .background(color = LightGray2),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Outlined.Add,
                                    tint = Gray,
                                    contentDescription = null,
                                )
                                Spacer(modifier = Modifier.size(15.dp))
                                Text(
                                    text = stringResource(R.string.add_photos),
                                    color = Gray,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.size(25.dp))

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            color = Light
                        )
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

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            color = Light
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

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            color = Light
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .height(70.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Light2),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(26.dp),
                                    painter = painterResource(id = R.drawable.ic_bell),
                                    tint = Gray,
                                    contentDescription = null,
                                )
                            }

                            Text(
                                modifier = Modifier
                                    .padding(start = 12.dp),
                                text = "30 minutes before",
                                color = Black,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            if (state.isEditMode) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Filled.ChevronRight,
                                    tint = Black,
                                    contentDescription = stringResource(R.string.edit)
                                )
                            }
                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            color = Light
                        )

                        Spacer(modifier = Modifier.size(30.dp))
                    }

                    item {
                        Row(
                            modifier = Modifier.padding(start = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.visitors),
                                color = Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                            Spacer(modifier = Modifier.width(20.dp))

                            if (state.isEditMode) {
                                Box(
                                    modifier = Modifier
                                        .size(35.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Light2)
                                        .clickable {

                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        modifier = Modifier.size(30.dp),
                                        imageVector = Icons.Filled.Add,
                                        tint = Gray,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.size(36.dp))
                    }

                    item {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            VisitorTypePill(
                                modifier = Modifier.weight(1f),
                                visitorType = VisitorType.All(isSelected = true)
                            ) {}
                            VisitorTypePill(
                                modifier = Modifier.weight(1f),
                                visitorType = VisitorType.Going(isSelected = false)
                            ) {}
                            VisitorTypePill(
                                modifier = Modifier.weight(1f),
                                visitorType = VisitorType.NotGoing(isSelected = false)
                            ) {}
                        }

                        Spacer(modifier = Modifier.size(20.dp))
                    }

                    item {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(R.string.going),
                            color = DarkGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.size(15.dp))
                    }

                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            (1..3).forEach { _ ->
                                VisitorRow(
                                    visitor = Attendee(
                                        initials = "MK",
                                        fullName = "Melvin Alex Kucuk"
                                    ),
                                    isEditMode = state.isEditMode
                                ) {}
                            }
                        }

                        Spacer(modifier = Modifier.size(20.dp))
                    }

                    item {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(R.string.not_going),
                            color = DarkGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.size(15.dp))
                    }

                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            (1..3).forEach { _ ->
                                VisitorRow(
                                    visitor = Attendee(
                                        initials = "MK",
                                        fullName = "Melvin Alex Kucuk"
                                    ),
                                    isEditMode = state.isEditMode
                                ) {}
                            }
                        }

                        Spacer(modifier = Modifier.size(45.dp))
                    }

                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.clickable { },
                                text = stringResource(R.string.delete_event),
                                color = Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.size(20.dp))
                    }

                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(72.dp)
                                    .height(2.dp)
                                    .clip(CircleShape)
                                    .background(Black)
                            )
                        }

                        Spacer(modifier = Modifier.size(20.dp))
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