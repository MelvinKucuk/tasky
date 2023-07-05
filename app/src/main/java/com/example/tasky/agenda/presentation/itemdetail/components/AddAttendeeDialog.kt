package com.example.tasky.agenda.presentation.itemdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tasky.R
import com.example.tasky.authentication.presentation.components.TaskyTextField
import com.example.tasky.ui.theme.Black

@Composable
fun AddAttendeeDialog(
    text: String,
    isValid: Boolean,
    isEnabled: Boolean,
    textValueChanged: (String) -> Unit,
    onCloseClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = { }) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(White)
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.End
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onCloseClick() },
                imageVector = Icons.Filled.Close,
                tint = Black,
                contentDescription = stringResource(R.string.close)
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.add_visitor),
                fontSize = 20.sp,
                color = Black,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            TaskyTextField(
                value = text,
                modifier = Modifier.padding(0.dp),
                placeholder = stringResource(R.string.email_address),
                imeAction = ImeAction.Next,
                isValid = isValid
            ) { emailValue ->
                textValueChanged(emailValue)
            }

            Spacer(modifier = Modifier.height(30.dp))

            DialogButton(
                text = stringResource(R.string.add),
                isEnabled = isEnabled
            ) {
                onAddClick()
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddAttendeeDialogPreview() {
    AddAttendeeDialog(
        text = "",
        isValid = false,
        isEnabled = true,
        textValueChanged = {},
        onCloseClick = {},
        onAddClick = {}
    )
}

@Preview(showSystemUi = true)
@Composable
fun AddAttendeeDialogPreviewValid() {
    AddAttendeeDialog(
        text = "mkucuk@gmail.com",
        isValid = true,
        isEnabled = true,
        textValueChanged = {},
        onCloseClick = {},
        onAddClick = {}
    )
}

@Preview(showSystemUi = true)
@Composable
fun AddAttendeeDialogPreviewDisabled() {
    AddAttendeeDialog(
        text = "",
        isValid = false,
        isEnabled = false,
        textValueChanged = {},
        onCloseClick = {},
        onAddClick = {}
    )
}