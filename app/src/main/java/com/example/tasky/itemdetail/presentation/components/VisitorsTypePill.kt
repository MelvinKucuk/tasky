package com.example.tasky.itemdetail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.itemdetail.presentation.model.VisitorType
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.DarkGray
import com.example.tasky.ui.theme.Light2

@Composable
fun VisitorTypePill(
    visitorType: VisitorType,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(30.dp)
            .clip(CircleShape)
            .background(
                if (visitorType.isSelected) {
                    Black
                } else {
                    Light2
                }
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(visitorType.name),
            color =
            if (visitorType.isSelected) {
                White
            } else {
                DarkGray
            },
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
fun VisitorTypePillPreviewSelected() {
    VisitorTypePill(
        modifier = Modifier.width(60.dp),
        visitorType = VisitorType.All(isSelected = true)
    ) {}
}

@Preview
@Composable
fun VisitorTypePillPreviewNotSelected() {
    VisitorTypePill(
        modifier = Modifier.width(60.dp),
        visitorType = VisitorType.All(isSelected = false)
    ) {}
}