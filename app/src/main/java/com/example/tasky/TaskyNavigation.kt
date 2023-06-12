package com.example.tasky

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tasky.agenda.presentation.AgendaScreen
import com.example.tasky.agenda.presentation.viewmodel.AgendaViewModel
import com.example.tasky.authentication.presentation.login.LoginScreen
import com.example.tasky.authentication.presentation.login.viewmodel.LoginEvent
import com.example.tasky.authentication.presentation.login.viewmodel.LoginViewModel
import com.example.tasky.authentication.presentation.signup.SignUpScreen
import com.example.tasky.authentication.presentation.signup.viewmodel.SignUpEvent
import com.example.tasky.authentication.presentation.signup.viewmodel.SignUpViewModel
import com.example.tasky.core.util.ObserveBoolean
import com.example.tasky.core.util.ObserveError
import com.example.tasky.core.util.makeToast

@Composable
fun TaskyNavigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(TaskyRoutes.LoginScreen.route) {
            val viewModel: LoginViewModel = hiltViewModel()

            with(viewModel.state) {
                ObserveError(errorMessage) {
                    viewModel.onEvent(LoginEvent.ErrorShown)
                }
                ObserveBoolean(onLoginSucceed) {
                    viewModel.onEvent(LoginEvent.LoginNavigated)
                    navController.navigate(TaskyRoutes.AgendaScreen.route) {
                        popUpTo(TaskyRoutes.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
                ObserveBoolean(navigateToSignUp) {
                    viewModel.onEvent(LoginEvent.SignUpNavigated)
                    navController.navigate(TaskyRoutes.SignUpScreen.route)
                }
            }
            LoginScreen(
                loginState = viewModel.state,
                onLoginEvent = viewModel::onEvent
            )
        }

        composable(TaskyRoutes.SignUpScreen.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            val context = LocalContext.current

            with(viewModel.state) {
                ObserveError(errorMessage) {
                    viewModel.onEvent(SignUpEvent.ErrorShown)
                }
                ObserveBoolean(navigateBack) {
                    navController.popBackStack()
                }
                ObserveBoolean(onSignUpSucceed) {
                    viewModel.onEvent(SignUpEvent.SignUpNavigated)
                    navController.popBackStack()
                    makeToast(context, R.string.account_created)
                }
            }

            SignUpScreen(
                signUpState = viewModel.state,
                onSignUpEvent = viewModel::onEvent
            )
        }

        composable(TaskyRoutes.AgendaScreen.route) {
            val viewModel: AgendaViewModel = hiltViewModel()

            AgendaScreen(state = viewModel.state)
        }
    }
}