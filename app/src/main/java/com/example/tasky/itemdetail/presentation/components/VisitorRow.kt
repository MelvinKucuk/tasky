package com.example.tasky.itemdetail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.core.domain.model.Attendee
import com.example.tasky.ui.theme.DarkGray
import com.example.tasky.ui.theme.Light2
import com.example.tasky.ui.theme.LightBlue2

@Composable
fun VisitorRow(
    visitor: Attendee,
    isEditMode: Boolean,
    modifier: Modifier = Modifier,
    onDeleteClick: (Attendee) -> Unit,
) {
    Box(
        modifier = modifier
            .height(46.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Light2)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VisitorInitials(visitor.initials)

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = visitor.fullName,
                color = DarkGray,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            if (visitor.isUserEventCreator) {
                Text(
                    text = stringResource(R.string.creator),
                    color = LightBlue2,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }

            if (isEditMode && !visitor.isUserEventCreator) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            onDeleteClick(visitor)
                        },
                    painter = painterResource(id = R.drawable.ic_delete),
                    tint = DarkGray,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
fun VisitorRowPreview() {
    VisitorRow(
        visitor = Attendee(
            fullName = "Melvin Alex Kucuk"
        ),
        isEditMode = false
    ) {}
}

@Preview
@Composable
fun VisitorRowCreatorPreview() {
    VisitorRow(
        visitor = Attendee(
            fullName = "Melvin Alex Kucuk",
            isUserEventCreator = true
        ),
        isEditMode = false
    ) {}
}

@Preview
@Composable
fun VisitorRowEditModeCreatorPreview() {
    VisitorRow(
        visitor = Attendee(
            fullName = "Melvin Alex Kucuk",
            isUserEventCreator = true
        ),
        isEditMode = true
    ) {}
}

@Preview
@Composable
fun VisitorRowEditModePreview() {
    VisitorRow(
        visitor = Attendee(
            fullName = "Melvin Alex Kucuk"
        ),
        isEditMode = true
    ) {}
}