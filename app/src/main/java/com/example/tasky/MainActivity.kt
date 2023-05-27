package com.example.tasky

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tasky.authentication.presentation.login.LoginScreen
import com.example.tasky.authentication.presentation.login.viewmodel.LoginViewModel
import com.example.tasky.ui.theme.TaskyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ObserveError()
                    ObserveLogin()
                    ObserveSignUp()
                    LoginScreen(
                        loginState = loginViewModel.state,
                        onLoginEvent = loginViewModel::onEvent
                    )
                }
            }
        }
    }

    @Composable
    private fun ObserveError() {
        LaunchedEffect(key1 = loginViewModel.state.errorMessage) {
            if (loginViewModel.state.errorMessage.isNotEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    loginViewModel.state.errorMessage,
                    Toast.LENGTH_LONG
                ).show()
                loginViewModel.errorShown()
            }
        }
    }

    @Composable
    private fun ObserveLogin() {
        LaunchedEffect(key1 = loginViewModel.state.onLoginSucceed) {
            if (loginViewModel.state.onLoginSucceed) {
                //TODO change to real implementation
                Toast.makeText(
                    this@MainActivity,
                    "Login succeed",
                    Toast.LENGTH_LONG
                ).show()
                loginViewModel.loginNavigated()
            }
        }
    }

    @Composable
    private fun ObserveSignUp() {
        LaunchedEffect(key1 = loginViewModel.state.navigateToSignUp) {
            if (loginViewModel.state.navigateToSignUp) {
                //TODO change to real implementation
                Toast.makeText(
                    this@MainActivity,
                    "Navigate to SignUp",
                    Toast.LENGTH_LONG
                ).show()
                loginViewModel.signUpNavigated()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskyTheme {
        Greeting("Android")
    }
}