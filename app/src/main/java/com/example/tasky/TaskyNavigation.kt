package com.example.tasky

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tasky.agenda.presentation.edit.EditScreen
import com.example.tasky.agenda.presentation.edit.viewmodel.EditEvent
import com.example.tasky.agenda.presentation.edit.viewmodel.EditViewModel
import com.example.tasky.agenda.presentation.home.AgendaScreen
import com.example.tasky.agenda.presentation.home.viewmodel.AgendaViewModel
import com.example.tasky.agenda.presentation.itemdetail.EventDetailScreen
import com.example.tasky.agenda.presentation.itemdetail.viewmodel.EventDetailEvent
import com.example.tasky.agenda.presentation.itemdetail.viewmodel.EventDetailViewModel
import com.example.tasky.authentication.presentation.login.LoginScreen
import com.example.tasky.authentication.presentation.login.viewmodel.LoginEvent
import com.example.tasky.authentication.presentation.login.viewmodel.LoginViewModel
import com.example.tasky.authentication.presentation.signup.SignUpScreen
import com.example.tasky.authentication.presentation.signup.viewmodel.SignUpEvent
import com.example.tasky.authentication.presentation.signup.viewmodel.SignUpViewModel
import com.example.tasky.core.util.Observe
import com.example.tasky.core.util.ObserveBoolean
import com.example.tasky.core.util.ObserveError
import com.example.tasky.core.util.makeToast

@Composable
fun TaskyNavigation(
    navController: NavHostController,
    startDestination: String,
    onLogout: () -> Unit
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

            with(viewModel.state) {
                ObserveError(errorMessage) {
                    viewModel.onEvent(com.example.tasky.agenda.presentation.home.viewmodel.AgendaEvent.ErrorHandled)
                }
                ObserveBoolean(isLoggedOut) {
                    onLogout()
                    navController.navigate(TaskyRoutes.LoginScreen.route) {
                        popUpTo(TaskyRoutes.AgendaScreen.route) {
                            inclusive = true
                        }
                    }
                    viewModel.onEvent(com.example.tasky.agenda.presentation.home.viewmodel.AgendaEvent.LogoutHandled)
                }
                Observe(navigateToEventDetail) { eventId ->
                    navController.navigate(TaskyRoutes.EventDetailScreen.getDestination(eventId))
                    viewModel.onEvent(com.example.tasky.agenda.presentation.home.viewmodel.AgendaEvent.EventNavigationHandled)
                }
            }
            AgendaScreen(state = viewModel.state, onEvent = viewModel::onEvent)
        }

        composable(
            route = TaskyRoutes.EventDetailScreen.getCompleteRoute(),
            arguments = listOf(
                navArgument(TaskyRoutes.EventDetailScreen.EVENT_ID) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val viewModel: EventDetailViewModel = hiltViewModel()

            viewModel.setEditedText(entry.savedStateHandle)

            with(viewModel.state) {
                ObserveBoolean(navigateBack) {
                    navController.popBackStack()
                    viewModel.onEvent(EventDetailEvent.CloseClickResolved)
                }

                Observe(observer = navigateEditTitle) {
                    navController.navigate(
                        TaskyRoutes.EditScreen.getDestination(
                            text = it,
                            isTitle = true
                        )
                    )
                    viewModel.onEvent(EventDetailEvent.NavigateEditTitleResolved)
                }

                Observe(observer = navigateEditDescription) {
                    navController.navigate(
                        TaskyRoutes.EditScreen.getDestination(
                            text = it,
                            isTitle = false
                        )
                    )
                    viewModel.onEvent(EventDetailEvent.NavigateEditDescriptionResolved)
                }
            }

            EventDetailScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent
            )
        }

        composable(
            route = TaskyRoutes.EditScreen.getCompleteRoute(),
            arguments = listOf(
                navArgument(TaskyRoutes.EditScreen.TEXT) {
                    type = NavType.StringType
                },
                navArgument(TaskyRoutes.EditScreen.IS_TITLE) {
                    type = NavType.BoolType
                }
            )
        ) {
            val viewModel: EditViewModel = hiltViewModel()

            with(viewModel.state) {
                Observe(observer = navigateBack) {
                    navController.popBackStack()
                    viewModel.onEvent(EditEvent.OnBackResolved)
                }

                Observe(observer = navigateWithResult) {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(TaskyRoutes.EditScreen.TEXT, it)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(TaskyRoutes.EditScreen.IS_TITLE, isTitle)
                    navController.popBackStack()
                    viewModel.onEvent(EditEvent.OnSaveResolved)
                }
            }

            EditScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent
            )
        }
    }
}