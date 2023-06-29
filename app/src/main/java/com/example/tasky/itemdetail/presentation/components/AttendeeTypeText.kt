package com.example.tasky.itemdetail.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.ui.theme.DarkGray

@Composable
fun AttendeeTypeText(
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.padding(start = 16.dp),
        text = stringResource(text),
        color = DarkGray,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )

    Spacer(modifier = Modifier.size(15.dp))
}

@Preview
@Composable
fun AttendeeTypeTextPreview() {
    AttendeeTypeText(R.string.going)
}