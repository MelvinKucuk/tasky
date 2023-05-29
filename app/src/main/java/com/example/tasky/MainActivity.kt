package com.example.tasky

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tasky.authentication.presentation.login.LoginScreen
import com.example.tasky.authentication.presentation.login.viewmodel.LoginEvent
import com.example.tasky.authentication.presentation.login.viewmodel.LoginViewModel
import com.example.tasky.authentication.presentation.signup.SignUpScreen
import com.example.tasky.authentication.presentation.signup.viewmodel.SignUpEvent
import com.example.tasky.authentication.presentation.signup.viewmodel.SignUpViewModel
import com.example.tasky.ui.theme.TaskyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskyTheme {
                val navController = rememberNavController()
                TaskyNavigation(navController)
            }
        }
    }

    @Composable
    private fun TaskyNavigation(navController: NavHostController) {
        NavHost(navController = navController, startDestination = loginRoute) {
            composable(loginRoute) {
                val viewModel: LoginViewModel = hiltViewModel()
                ObserveError(viewModel.state.errorMessage) {
                    viewModel.onEvent(LoginEvent.ErrorShown)
                }
                ObserveBoolean(viewModel.state.onLoginSucceed) {
                    viewModel.onEvent(LoginEvent.LoginNavigated)
                    // TODO when home implemented
                }
                ObserveBoolean(viewModel.state.navigateToSignUp) {
                    viewModel.onEvent(LoginEvent.SignUpNavigated)
                    navController.navigate(signUpRoute)
                }
                LoginScreen(
                    loginState = viewModel.state,
                    onLoginEvent = viewModel::onEvent
                )
            }

            composable(signUpRoute) {
                val viewModel: SignUpViewModel = hiltViewModel()
                ObserveError(viewModel.state.errorMessage) {
                    viewModel.onEvent(SignUpEvent.ErrorShown)
                }
                ObserveBoolean(viewModel.state.navigateBack) {
                    navController.popBackStack()
                }
                ObserveBoolean(observer = viewModel.state.onSignUpSucceed) {
                    viewModel.onEvent(SignUpEvent.SignUpNavigated)
                    navController.popBackStack()
                    Toast.makeText(
                        this@MainActivity,
                        R.string.account_created,
                        Toast.LENGTH_LONG
                    ).show()
                }
                SignUpScreen(
                    signUpState = viewModel.state,
                    onSignUpEvent = viewModel::onEvent
                )
            }
        }
    }

    @Composable
    private fun ObserveError(errorMessage: String?, onErrorShown: () -> Unit) {
        LaunchedEffect(key1 = errorMessage) {
            if (errorMessage != null) {
                Toast.makeText(
                    this@MainActivity,
                    errorMessage,
                    Toast.LENGTH_LONG
                ).show()
                onErrorShown()
            }
        }
    }

    @Composable
    private fun ObserveBoolean(observer: Boolean?, navigateToHome: () -> Unit) {
        LaunchedEffect(key1 = observer) {
            if (observer == true) {
                navigateToHome()
            }
        }
    }

    companion object {
        private const val loginRoute = "login_screen"
        private const val signUpRoute = "sign_up_screen"
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