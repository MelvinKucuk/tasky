package com.example.tasky.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tasky.R
import com.example.tasky.authentication.presentation.login.LoginNavigation
import com.example.tasky.authentication.presentation.login.LoginScreen
import com.example.tasky.authentication.presentation.login.viewmodel.LoginViewModel
import com.example.tasky.authentication.presentation.signup.SignUpNavigation
import com.example.tasky.authentication.presentation.signup.SignUpScreen
import com.example.tasky.authentication.presentation.signup.viewmodel.SignUpViewModel
import com.example.tasky.core.util.makeToast

@Composable
fun TaskyNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = TaskyRoutes.LoginScreen.route) {
        composable(TaskyRoutes.LoginScreen.route) {
            val viewModel: LoginViewModel = hiltViewModel()

            LoginScreen(
                loginState = viewModel.state,
                onLoginEvent = viewModel::onEvent
            ) {
                when (it) {
                    LoginNavigation.SignUp -> navController.navigate(TaskyRoutes.SignUpScreen.route)
                    LoginNavigation.Agenda -> {
                        //TODO when Agenda implemented
                    }
                }
            }
        }

        composable(TaskyRoutes.SignUpScreen.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            val context = LocalContext.current

            SignUpScreen(
                signUpState = viewModel.state,
                onSignUpEvent = viewModel::onEvent
            ) {
                when (it) {
                    SignUpNavigation.Back -> navController.popBackStack()
                    SignUpNavigation.Login -> {
                        navController.popBackStack()
                        makeToast(context, R.string.account_created)
                    }
                }
            }
        }
    }
}