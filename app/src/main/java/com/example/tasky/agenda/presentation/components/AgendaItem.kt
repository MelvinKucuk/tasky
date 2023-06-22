package com.example.tasky.agenda.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.presentation.AgendaItemEvent
import com.example.tasky.agenda.presentation.MenuItem
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.Gray
import com.example.tasky.ui.theme.Green
import com.example.tasky.ui.theme.LightGreen

@Composable
fun EventItem(
    event: AgendaItem.Event,
    menuItems: List<MenuItem>,
    onAgendaItemEvent: (AgendaItemEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AgendaItem(
        title = event.title,
        description = event.description,
        date = event.date,
        menuItems = menuItems,
        onAgendaEvent = onAgendaItemEvent,
        containerColor = LightGreen,
        contentColor = Black,
        modifier = modifier
    )
}

@Composable
fun ReminderItem(
    reminder: AgendaItem.Reminder,
    menuItems: List<MenuItem>,
    onAgendaEvent: (AgendaItemEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AgendaItem(
        title = reminder.title,
        description = reminder.description,
        date = reminder.date,
        menuItems = menuItems,
        onAgendaEvent = onAgendaEvent,
        containerColor = Gray,
        contentColor = Black,
        modifier = modifier
    )
}

@Composable
fun TaskItem(
    task: AgendaItem.Task,
    menuItems: List<MenuItem>,
    onAgendaEvent: (AgendaItemEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    AgendaItem(
        title = task.title,
        description = task.description,
        date = task.date,
        menuItems = menuItems,
        onAgendaEvent = onAgendaEvent,
        isTask = true,
        isDone = task.isDone,
        containerColor = Green,
        modifier = modifier
    )
}

@Composable
fun AgendaItem(
    title: String,
    description: String,
    date: String,
    menuItems: List<MenuItem>,
    onAgendaEvent: (AgendaItemEvent) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Green,
    contentColor: Color = Color.White,
    isTask: Boolean = false,
    isDone: Boolean = false,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 14.dp, end = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Column(
            modifier = Modifier.padding(
                start = 15.dp,
                top = 17.dp,
                end = 15.dp,
                bottom = 12.dp
            ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    Spacer(modifier = Modifier.size(5.dp))
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .then(
                                if (isTask) {
                                    Modifier.toggleable(value = isDone) { isDone ->
                                        onAgendaEvent(AgendaItemEvent.DoneClick(isDone))
                                    }
                                } else Modifier),
                        imageVector =
                        if (isDone)
                            Icons.Outlined.CheckCircle
                        else
                            Icons.Outlined.Circle,
                        contentDescription = stringResource(
                            R.string.agenda_item_check
                        ),
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration =
                        if (isDone)
                            TextDecoration.LineThrough
                        else null
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = description,
                        fontSize = 14.sp,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Column {
                    Spacer(modifier = Modifier.size(2.dp))
                    var showMenu by remember {
                        mutableStateOf(false)
                    }
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                showMenu = true
                            },
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = stringResource(
                            R.string.agenda_item_more_options
                        )
                    )

                    TaskyDropdownMenu(
                        showDropdown = showMenu,
                        items = menuItems,
                        onClick = {
                            onAgendaEvent(AgendaItemEvent.MenuClick(it))
                        },
                        onDismiss = {
                            showMenu = false
                        }
                    )
                }
            }

            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 7.dp),
                text = date,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AgendaItemPreview() {
    AgendaItem(
        title = "Project X",
        description = "Just work",
        date = "Mar 5, 10:00",
        menuItems = listOf(),
        isDone = false,
        onAgendaEvent = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AgendaItemBigDescriptionPreview() {
    AgendaItem(
        title = "Project X",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam tempus leo et dolor ultrices, id vestibulum lectus lobortis. Nulla hendrerit ex vitae dui finibus porta. Vestibulum sit amet feugiat justo, fermentum finibus elit. Duis quis molestie lectus, at dapibus magna. Maecenas sagittis justo quis leo imperdiet, commodo cursus lacus aliquam.",
        date = "Mar 5, 10:00",
        menuItems = listOf(),
        isDone = false,
        onAgendaEvent = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AgendaItemTaskDonePreview() {
    AgendaItem(
        title = "Project X",
        description = "Just work",
        date = "Mar 5, 10:00",
        menuItems = listOf(),
        isDone = true,
        isTask = true,
        onAgendaEvent = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AgendaItemTaskNotDonePreview() {
    AgendaItem(
        title = "Project X",
        description = "Just work",
        date = "Mar 5, 10:00",
        menuItems = listOf(),
        isTask = true,
        onAgendaEvent = {}
    )
}