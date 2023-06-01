package com.example.tasky.agenda.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.agenda.presentation.model.Day
import com.example.tasky.ui.theme.DarkGray
import com.example.tasky.ui.theme.Gray2
import com.example.tasky.ui.theme.Orange
import com.example.tasky.ui.theme.TaskyTheme

@Composable
fun DayPill(
    day: Day,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = 40.dp, height = 60.dp)
            .background(
                color = if (day.isSelected) Orange else White,
                shape = CircleShape
            )
            .clip(CircleShape)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = day.letter,
                fontSize = 11.sp,
                color = if (day.isSelected) DarkGray else Gray2,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = day.number,
                fontSize = 17.sp,
                color = DarkGray,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun DayPillPreview() {
    TaskyTheme {
        DayPill(
            Day(
                letter = "S",
                number = "5"
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun DayPillSelectedPreview() {
    TaskyTheme {
        DayPill(
            Day(
                letter = "S",
                number = "5",
            )
        )
    }
}