package com.example.tasky.agenda.presentation.itemdetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Divider
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
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.Light

@Composable
fun DetailTitle(
    title: String,
    isEditMode: Boolean,
    modifier: Modifier = Modifier,
    onEditClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp)
            .clickable(enabled = isEditMode) { onEditClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Outlined.Circle,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = title,
            color = Black,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        )

        if (isEditMode) {
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

@Preview
@Composable
fun DetailTitlePreview() {
    DetailTitle(
        title = "Title of event",
        isEditMode = false
    ) {}
}