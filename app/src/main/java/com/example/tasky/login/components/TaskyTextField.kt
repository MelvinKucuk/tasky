package com.example.tasky.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tasky.R
import com.example.tasky.ui.theme.DarkGray
import com.example.tasky.ui.theme.Gray
import com.example.tasky.ui.theme.Gray2
import com.example.tasky.ui.theme.Green
import com.example.tasky.ui.theme.LightBlue2
import com.example.tasky.ui.theme.LightGray2
import com.example.tasky.ui.theme.Red

@Composable
fun TaskyTextField(
    isPassword: Boolean = false,
    isValid: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (String) -> Unit
) {
    var value by remember { mutableStateOf(TextFieldValue()) }

    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange(value.text)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            errorCursorColor = Red,
            errorIndicatorColor = Red,
            errorLabelColor = Red,
            errorContainerColor = LightGray2,
            errorTrailingIconColor = Gray2,
            focusedTextColor = DarkGray,
            focusedPlaceholderColor = Gray,
            focusedLeadingIconColor = Gray,
            focusedContainerColor = LightGray2,
            focusedIndicatorColor = LightBlue2,
            focusedTrailingIconColor = if (isValid) Green else Gray2,
            unfocusedContainerColor = LightGray2,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedTrailingIconColor = if (isValid) Green else Gray2,
            cursorColor = DarkGray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        trailingIcon = {
            when {
                isPassword -> IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = if (showPassword)
                            stringResource(R.string.show_password)
                        else
                            stringResource(R.string.hide_password)
                    )
                }

                isValid -> Icon(
                    painter = painterResource(id = R.drawable.ic_valid),
                    contentDescription = stringResource(R.string.success_icon)
                )
            }
        },
        visualTransformation = if (isPassword.not() || showPassword)
            VisualTransformation.None
        else
            PasswordVisualTransformation()
    )
}