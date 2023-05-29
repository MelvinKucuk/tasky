package com.example.tasky.authentication.presentation.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.authentication.presentation.components.BackButton
import com.example.tasky.authentication.presentation.components.ButtonWithLoader
import com.example.tasky.authentication.presentation.components.TaskyPasswordTextField
import com.example.tasky.authentication.presentation.components.TaskyTextField
import com.example.tasky.authentication.presentation.signup.viewmodel.SignUpEvent
import com.example.tasky.authentication.presentation.signup.viewmodel.SignUpState
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.TaskyTheme

@Composable
fun SignUpScreen(
    signUpState: SignUpState,
    onSignUpEvent: (SignUpEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Black,
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top = 46.dp),
                text = stringResource(R.string.create_your_account),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.size(size = 46.dp))

            Scaffold(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 25.dp,
                            topEnd = 25.dp
                        )
                    ),
                containerColor = Color.White
            ) { padding ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.size(50.dp))

                    TaskyTextField(
                        value = signUpState.nameValue,
                        placeholder = stringResource(R.string.name),
                        imeAction = ImeAction.Next,
                        isValid = signUpState.isValidName
                    ) { nameValue ->
                        onSignUpEvent(SignUpEvent.OnNameValueChanged(nameValue))
                    }
                    Spacer(modifier = Modifier.size(16.dp))

                    TaskyTextField(
                        value = signUpState.emailValue,
                        placeholder = stringResource(R.string.email_address),
                        imeAction = ImeAction.Next,
                        isValid = signUpState.isValidEmail
                    ) { emailValue ->
                        onSignUpEvent(SignUpEvent.OnEmailValueChanged(emailValue))
                    }
                    Spacer(modifier = Modifier.size(16.dp))

                    TaskyPasswordTextField(
                        value = signUpState.passwordValue,
                        placeholder = stringResource(R.string.password),
                        showPassword = signUpState.showPassword,
                        keyboardType = KeyboardType.Password,
                        onShowPasswordChange = { showPassword ->
                            onSignUpEvent(SignUpEvent.OnShowPasswordValueChanged(showPassword))
                        }
                    ) { passwordValue ->
                        onSignUpEvent(SignUpEvent.OnPasswordValueChanged(passwordValue))
                    }
                    Spacer(modifier = Modifier.size(25.dp))
                    ButtonWithLoader(
                        text = stringResource(R.string.get_started),
                        isLoading = signUpState.isLoading
                    ) {
                        onSignUpEvent(SignUpEvent.OnSignUpClicked)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, bottom = 68.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        BackButton {
                            onSignUpEvent(SignUpEvent.OnBackClicked)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    TaskyTheme {
        SignUpScreen(SignUpState()) {}
    }
}