package com.example.tasky.agenda.presentation.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.agenda.presentation.edit.components.EditHeader
import com.example.tasky.agenda.presentation.edit.model.EditType
import com.example.tasky.agenda.presentation.edit.viewmodel.EditEvent
import com.example.tasky.agenda.presentation.edit.viewmodel.EditState
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.Light

@Composable
fun EditScreen(
    state: EditState,
    onEvent: (EditEvent) -> Unit
) {
    Scaffold {
        Column(modifier = Modifier.padding(it)) {

            EditHeader(
                editType = state.editType,
                onSaveClick = {
                    onEvent(EditEvent.OnSaveClicked)
                },
                onBackClick = {
                    onEvent(EditEvent.OnBackClicked)
                }
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Light
            )

            BasicTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 30.dp),
                value = state.text,
                onValueChange = { newText ->
                    onEvent(EditEvent.OnTextChanged(newText))
                },
                textStyle = TextStyle.Default.copy(
                    fontWeight =
                    when (state.editType) {
                        EditType.Title -> FontWeight.Medium
                        EditType.Description -> FontWeight.Normal
                    },
                    fontSize =
                    when (state.editType) {
                        EditType.Title -> 26.sp
                        EditType.Description -> 16.sp
                    },
                    color = Black
                ),
            )
        }
    }
}

@Preview
@Composable
fun EditScreenPreview() {
    EditScreen(EditState(text = "This is a title")) {}
}