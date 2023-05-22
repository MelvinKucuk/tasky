package com.example.tasky.authentication.presentation.login.components

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
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    isValidEmail: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (TextFieldValue) -> Unit,
) {
    BaseTaskyTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        isValidEmail = isValidEmail,
        keyboardType = keyboardType,
        imeAction = imeAction
    )
}

@Composable
fun TaskyPasswordTextField(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    showPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onShowPasswordChange: (Boolean) -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit,
) {
    BaseTaskyTextField(
        modifier = modifier,
        value = value,
        isPassword = true,
        showPassword = showPassword,
        onValueChange = onValueChange,
        keyboardType = keyboardType,
        imeAction = imeAction,
        onShowPasswordChange = onShowPasswordChange
    )
}

@Composable
private fun BaseTaskyTextField(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isValidEmail: Boolean = false,
    showPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (TextFieldValue) -> Unit,
    onShowPasswordChange: (Boolean) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = {
            onValueChange(value)
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
            focusedTrailingIconColor = if (isValidEmail) Green else Gray2,
            unfocusedContainerColor = LightGray2,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedTrailingIconColor = if (isValidEmail) Green else Gray2,
            cursorColor = DarkGray
        ),
        trailingIcon = {
            TextFieldTrailingIcon(
                isPassword = isPassword,
                onShowPasswordChange = onShowPasswordChange,
                showPassword = showPassword,
                isValidEmail = isValidEmail
            )
        },
        visualTransformation = if (!isPassword || showPassword)
            VisualTransformation.None
        else
            PasswordVisualTransformation()
    )
}

@Composable
private fun TextFieldTrailingIcon(
    isPassword: Boolean,
    onShowPasswordChange: (Boolean) -> Unit,
    showPassword: Boolean,
    isValidEmail: Boolean
) {
    when {
        isPassword -> IconButton(onClick = { onShowPasswordChange(!showPassword) }) {
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

        isValidEmail -> Icon(
            painter = painterResource(id = R.drawable.ic_valid),
            contentDescription = stringResource(R.string.success_icon)
        )
    }
}