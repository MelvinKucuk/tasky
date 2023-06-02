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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.agenda.presentation.model.Agenda
import com.example.tasky.ui.theme.Green

@Composable
fun AgendaItem(
    agendaItem: Agenda.AgendaUI,
    onDoneClicked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onMoreOptionsClicked: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 14.dp, end = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Green,
            contentColor = Color.White
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
                            .toggleable(value = agendaItem.isDone) { isDone ->
                                onDoneClicked(isDone)
                            },
                        imageVector =
                        if (agendaItem.isDone)
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
                        text = agendaItem.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration =
                        if (agendaItem.isDone)
                            TextDecoration.LineThrough
                        else null
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = agendaItem.description,
                        fontSize = 14.sp,
                    )
                }
                Column {
                    Spacer(modifier = Modifier.size(2.dp))
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                onMoreOptionsClicked()
                            },
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = stringResource(
                            R.string.agenda_item_more_options
                        )
                    )
                }
            }

            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 7.dp),
                text = "Mar 5, 10:30 - Mar 5, 11:00",
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AgendaItemPreview() {
    AgendaItem(
        Agenda.AgendaUI(
            title = "Project X",
            description = "Just work",
            date = "Mar 5, 10:00",
            isDone = false
        ),
        onDoneClicked = {}
    ) {}
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AgendaItemBigDescriptionPreview() {
    AgendaItem(
        Agenda.AgendaUI(
            title = "Project X",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam tempus leo et dolor ultrices, id vestibulum lectus lobortis. Nulla hendrerit ex vitae dui finibus porta. Vestibulum sit amet feugiat justo, fermentum finibus elit. Duis quis molestie lectus, at dapibus magna. Maecenas sagittis justo quis leo imperdiet, commodo cursus lacus aliquam.",
            date = "Mar 5, 10:00",
            isDone = false
        ),
        onDoneClicked = {}
    ) {}
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun AgendaItemDonePreview() {
    AgendaItem(
        Agenda.AgendaUI(
            title = "Project X",
            description = "Just work",
            date = "Mar 5, 10:00",
            isDone = true
        ),
        onDoneClicked = {}
    ) {}
}