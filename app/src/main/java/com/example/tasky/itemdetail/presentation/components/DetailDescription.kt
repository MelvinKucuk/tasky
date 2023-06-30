package com.example.tasky.itemdetail.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.ui.theme.Black

@Composable
fun DetailDescription(
    description: String,
    isEditMode: Boolean,
    modifier: Modifier = Modifier,
    onEditClicked: () -> Unit
) {
    Row(modifier = modifier.clickable(enabled = isEditMode) { onEditClicked() }) {
        Text(
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            text = description,
            color = Black,
            fontSize = 16.sp
        )

        if (isEditMode) {
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

@Preview
@Composable
fun DetailDescriptionPreview() {
    DetailDescription(
        description = "This is a description",
        isEditMode = false
    ) {

    }
}