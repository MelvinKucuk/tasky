package com.example.tasky.itemdetail.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.Light

@Composable
fun TimeDatePicker(
    time: String,
    timePrefix: String,
    date: String,
    isEditMode: Boolean,
    onTimeClicked: () -> Unit,
    onDateClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .height(70.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier
                    .clickable(enabled = isEditMode) { onTimeClicked() }
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 6.dp),
                    text = timePrefix,
                    color = Black,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.weight(0.1f))

                Text(
                    text = time,
                    color = Black,
                    fontSize = 16.sp
                )
            }

            if (isEditMode) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onTimeClicked() }
                        .weight(1f),
                    imageVector = Icons.Filled.ChevronRight,
                    tint = Black,
                    contentDescription = stringResource(R.string.edit)
                )
            } else {
                Spacer(modifier = Modifier
                    .size(30.dp)
                    .weight(1f))
            }

            Row(
                modifier = Modifier
                    .clickable(enabled = isEditMode) { onDateClicked() }
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 6.dp),
                    text = date,
                    color = Black,
                    fontSize = 16.sp
                )
            }

            if (isEditMode) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onDateClicked() },
                    imageVector = Icons.Filled.ChevronRight,
                    tint = Black,
                    contentDescription = stringResource(R.string.edit)
                )
            } else {
                Spacer(modifier = Modifier.size(30.dp))
            }

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = Light
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun TimeDatePickerPreview() {
    TimeDatePicker(
        time = "08:00",
        timePrefix = stringResource(id = R.string.from),
        date = "Jul 21 2023",
        isEditMode = false,
        onTimeClicked = {},
        onDateClicked = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun TimeDatePickerPreviewWithEditMode() {
    TimeDatePicker(
        time = "08:00",
        timePrefix = stringResource(id = R.string.from),
        date = "Jul 21 2023",
        isEditMode = true,
        onTimeClicked = {},
        onDateClicked = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun TimeDatePickerPreviewWithPrefixTo() {
    TimeDatePicker(
        time = "08:00",
        timePrefix = stringResource(R.string.to),
        date = "Jul 21 2023",
        isEditMode = false,
        onTimeClicked = {},
        onDateClicked = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun TimeDatePickerPreviewWithEditModeWithPrefixTo() {
    TimeDatePicker(
        time = "08:00",
        timePrefix = stringResource(R.string.to),
        date = "Jul 21 2023",
        isEditMode = true,
        onTimeClicked = {},
        onDateClicked = {}
    )
}