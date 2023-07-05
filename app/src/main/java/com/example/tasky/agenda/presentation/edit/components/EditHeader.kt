package com.example.tasky.agenda.presentation.edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.agenda.presentation.edit.model.EditType
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.Green

@Composable
fun EditHeader(
    editType: EditType,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.clickable { onBackClick() },
            imageVector = Icons.Filled.ChevronLeft,
            contentDescription = stringResource(R.string.back)
        )

        Text(
            text = when (editType) {
                EditType.Title -> stringResource(R.string.edit_title)
                EditType.Description -> stringResource(R.string.edit_description)
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Black
        )

        Text(
            modifier = Modifier.clickable { onSaveClick() },
            text = stringResource(R.string.save),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Green
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditHeaderPreview() {
    EditHeader(
        editType = EditType.Title,
        onSaveClick = {},
        onBackClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun EditHeaderPreviewNotTitle() {
    EditHeader(
        editType = EditType.Description,
        onSaveClick = {},
        onBackClick = {}
    )
}