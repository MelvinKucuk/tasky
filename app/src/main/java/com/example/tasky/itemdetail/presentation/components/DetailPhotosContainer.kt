package com.example.tasky.itemdetail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
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
import com.example.tasky.ui.theme.Gray
import com.example.tasky.ui.theme.Light
import com.example.tasky.ui.theme.LightGray2

@Composable
fun DetailPhotosContainer(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(color = LightGray2)
            .clickable { onAddClick() },
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

@Preview
@Composable
fun DetailPhotosContainerPreview() {
    DetailPhotosContainer {}
}