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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.tasky.R
import com.example.tasky.agenda.presentation.itemdetail.viewmodel.AttendeeDialogState
import com.example.tasky.authentication.presentation.components.ButtonWithLoader
import com.example.tasky.authentication.presentation.components.TaskyTextField
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.Green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAttendeeDialog(
    state: AttendeeDialogState,
    textValueChanged: (String) -> Unit,
    onCloseClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onDismissRequest = { onCloseClick() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(White)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
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
                value = state.email,
                modifier = Modifier.padding(0.dp),
                placeholder = stringResource(R.string.email_address),
                imeAction = ImeAction.Next,
                isValid = state.isValidEmail
            ) { emailValue ->
                textValueChanged(emailValue)
            }

            if (state.showError) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.there_is_no_user_with_that_email),
                    fontSize = 16.sp,
                    color = Red,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }

            if (state.showSuccess) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.visitor_added_successfully),
                    fontSize = 16.sp,
                    color = Green,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            ButtonWithLoader(
                text = stringResource(R.string.add),
                isEnabled = state.isEnabled,
                isLoading = state.isLoading,
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
        state = AttendeeDialogState(
            email = "",
            isValidEmail = false,
            isEnabled = true,
            isLoading = false,
            showError = false,
        ),
        textValueChanged = {},
        onCloseClick = {},
        onAddClick = {}
    )
}

@Preview(showSystemUi = true)
@Composable
fun AddAttendeeDialogPreviewValid() {
    AddAttendeeDialog(
        state = AttendeeDialogState(
            email = "",
            isValidEmail = true,
            isEnabled = true,
            isLoading = false,
            showError = false,
        ),
        textValueChanged = {},
        onCloseClick = {},
        onAddClick = {}
    )
}

@Preview(showSystemUi = true)
@Composable
fun AddAttendeeDialogPreviewDisabled() {
    AddAttendeeDialog(
        state = AttendeeDialogState(
            email = "",
            isValidEmail = false,
            isEnabled = false,
            isLoading = false,
            showError = false,
        ),
        textValueChanged = {},
        onCloseClick = {},
        onAddClick = {}
    )
}

@Preview(showSystemUi = true)
@Composable
fun AddAttendeeDialogPreviewWithError() {
    AddAttendeeDialog(
        state = AttendeeDialogState(
            email = "",
            isValidEmail = false,
            isEnabled = true,
            isLoading = false,
            showError = true,
        ),
        textValueChanged = {},
        onCloseClick = {},
        onAddClick = {}
    )
}

@Preview(showSystemUi = true)
@Composable
fun AddAttendeeDialogPreviewWithSuccess() {
    AddAttendeeDialog(
        state = AttendeeDialogState(
            email = "",
            isValidEmail = false,
            isEnabled = true,
            isLoading = false,
            showError = true,
            showSuccess = true
        ),
        textValueChanged = {},
        onCloseClick = {},
        onAddClick = {}
    )
}