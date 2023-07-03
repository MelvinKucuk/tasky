package com.example.tasky.agenda.presentation.itemdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.agenda.presentation.itemdetail.model.NotificationType
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.Gray
import com.example.tasky.ui.theme.Light
import com.example.tasky.ui.theme.Light2

@Composable
fun DetailReminder(
    text: String,
    isEditMode: Boolean,
    modifier: Modifier = Modifier,
    onOptionSelected: (NotificationType) -> Unit,
) {
    Column {
        var showMenu by remember {
            mutableStateOf(false)
        }
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .height(70.dp)
                .clickable(enabled = isEditMode) { showMenu = true },
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
                text = text,
                color = Black,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            if (isEditMode) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Filled.ChevronRight,
                    tint = Black,
                    contentDescription = stringResource(R.string.edit)
                )
            }
        }

        DropdownNotificationMenu(
            showDropdown = showMenu,
            items = NotificationType.values().toList(),
            onClick = {
                onOptionSelected(it)
                showMenu = false
            },
            onDismiss = {
                showMenu = false
            }
        )
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = Light
    )

    Spacer(modifier = Modifier.size(30.dp))
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun DetailReminderPreview() {
    DetailReminder(text = "30 minutes before", isEditMode = false) {

    }
}