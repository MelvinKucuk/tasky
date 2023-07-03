package com.example.tasky.agenda.presentation.itemdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.agenda.presentation.itemdetail.model.NotificationType
import com.example.tasky.ui.theme.Light

@Composable
fun DropdownNotificationMenu(
    showDropdown: Boolean,
    items: List<NotificationType>,
    onClick: (NotificationType) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset.Zero,
) {
    DropdownMenu(
        modifier = modifier.background(Color.White),
        expanded = showDropdown,
        onDismissRequest = {
            onDismiss()
        },
        offset = offset,
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            items.forEachIndexed { index, menuItem ->
                Box(
                    modifier = Modifier
                        .size(width = 225.dp, height = 55.dp)
                        .clickable {
                            onClick(menuItem)
                        },
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = stringResource(menuItem.type),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                }
                if (index != items.lastIndex) {
                    Divider(color = Light)
                }
            }
        }
    }
}
