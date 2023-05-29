package com.example.tasky.authentication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasky.R
import com.example.tasky.ui.theme.Black

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val roundedCornerShape = RoundedCornerShape(15.dp)
    Box(
        modifier = modifier
            .size(56.dp)
            .background(
                color = Black,
                shape = roundedCornerShape
            )
            .clip(roundedCornerShape)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(25.dp)
                .padding(start = 7.dp),
            imageVector = Icons.Filled.ArrowBackIos,
            tint = Color.White,
            contentDescription = stringResource(R.string.back_icon)
        )
    }
}