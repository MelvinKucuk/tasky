package com.example.tasky.authentication.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction.Companion
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.authentication.presentation.components.ButtonWithLoader
import com.example.tasky.authentication.presentation.components.TaskyPasswordTextField
import com.example.tasky.authentication.presentation.components.TaskyTextField
import com.example.tasky.authentication.presentation.login.viewmodel.LoginEvent
import com.example.tasky.authentication.presentation.login.viewmodel.LoginState
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.LightBlue
import com.example.tasky.ui.theme.LightGray
import com.example.tasky.ui.theme.TaskyTheme

@Composable
fun LoginScreen(
    loginState: LoginState,
    onLoginEvent: (LoginEvent) -> Unit
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
                text = stringResource(R.string.welcome_back),
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
                        modifier = Modifier.padding(horizontal = 16.dp),
                        value = loginState.emailValue,
                        placeholder = stringResource(R.string.email_address),
                        imeAction = Companion.Next,
                        isValid = loginState.isValidEmail
                    ) { emailValue ->
                        onLoginEvent(LoginEvent.OnEmailValueChanged(emailValue))
                    }
                    Spacer(modifier = Modifier.size(16.dp))

                    TaskyPasswordTextField(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        value = loginState.passwordValue,
                        placeholder = stringResource(R.string.password),
                        showPassword = loginState.showPassword,
                        keyboardType = KeyboardType.Password,
                        onShowPasswordChange = { showPassword ->
                            onLoginEvent(LoginEvent.OnShowPasswordValueChanged(showPassword))
                        }
                    ) { passwordValue ->
                        onLoginEvent(LoginEvent.OnPasswordValueChanged(passwordValue))
                    }
                    Spacer(modifier = Modifier.size(25.dp))
                    ButtonWithLoader(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(id = R.string.log_in),
                        isLoading = loginState.isLoading
                    ) {
                        onLoginEvent(LoginEvent.OnLoginClicked)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .padding(
                                bottom = 60.dp,
                                top = 10.dp,
                                start = 10.dp,
                                end = 10.dp
                            )
                            .clickable {
                                onLoginEvent(LoginEvent.OnSignUpClicked)
                            }
                    ) {
                        Text(
                            text = stringResource(R.string.dont_have_an_account),
                            color = LightGray,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = stringResource(R.string.sign_up),
                            color = LightBlue,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    TaskyTheme {
        LoginScreen(
            loginState = LoginState(),
            onLoginEvent = {}
        )
    }
}