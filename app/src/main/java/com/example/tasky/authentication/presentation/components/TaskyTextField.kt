package com.example.tasky.authentication.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.ui.theme.DarkGray
import com.example.tasky.ui.theme.Gray
import com.example.tasky.ui.theme.Gray2
import com.example.tasky.ui.theme.Green
import com.example.tasky.ui.theme.LightBlue2
import com.example.tasky.ui.theme.LightGray
import com.example.tasky.ui.theme.LightGray2
import com.example.tasky.ui.theme.Red

@Composable
fun TaskyTextField(
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    isValid: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (String) -> Unit,
) {
    BaseTaskyTextField(
        modifier = modifier,
        value = value,
        placeholder = placeholder,
        onValueChange = onValueChange,
        isValid = isValid,
        keyboardType = keyboardType,
        imeAction = imeAction
    )
}

@Composable
fun TaskyPasswordTextField(
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    showPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onShowPasswordChange: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {
    BaseTaskyTextField(
        modifier = modifier,
        value = value,
        placeholder = placeholder,
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
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    isPassword: Boolean = false,
    isValid: Boolean = false,
    showPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (String) -> Unit,
    onShowPasswordChange: (Boolean) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        placeholder = {
            if (placeholder != null) {
                Text(
                    text = placeholder,
                    color = LightGray,
                    fontSize = 16.sp
                )
            }
        },
        onValueChange = {
            onValueChange(it)
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
            errorTrailingIconColor = Gray,
            focusedTextColor = DarkGray,
            focusedPlaceholderColor = Gray2,
            focusedLeadingIconColor = Gray2,
            focusedContainerColor = LightGray2,
            focusedIndicatorColor = LightBlue2,
            focusedTrailingIconColor = if (isValid) Green else Gray,
            unfocusedContainerColor = LightGray2,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedTrailingIconColor = if (isValid) Green else Gray,
            cursorColor = DarkGray
        ),
        trailingIcon = {
            TextFieldTrailingIcon(
                isPassword = isPassword,
                onShowPasswordChange = onShowPasswordChange,
                showPassword = showPassword,
                isValid = isValid
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
    isValid: Boolean
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

        isValid -> Icon(
            painter = painterResource(id = R.drawable.ic_valid),
            contentDescription = stringResource(R.string.success_icon)
        )
    }
}