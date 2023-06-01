package com.example.tasky.agenda.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.tasky.R

@Composable
fun MonthButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            tint = Color.White,
            contentDescription = stringResource(R.string.dropdown_arrow)
        )
    }
}

@Preview
@Composable
fun MonthButtonPreview() {
    MonthButton(text = "MARCH") {}
}