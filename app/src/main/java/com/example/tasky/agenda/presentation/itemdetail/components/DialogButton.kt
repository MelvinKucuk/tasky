package com.example.tasky.agenda.presentation.itemdetail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.Gray

@Composable
fun DialogButton(
    text: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        enabled = isEnabled,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Black,
            contentColor = White,
            disabledContainerColor = Gray,
            disabledContentColor = White
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun DialogButtonPreview() {
    DialogButton(
        text = "ADD",
        isEnabled = true
    ) {}
}

@Preview
@Composable
fun DialogButtonPreviewDisabled() {
    DialogButton(
        text = "ADD",
        isEnabled = false
    ) {}
}