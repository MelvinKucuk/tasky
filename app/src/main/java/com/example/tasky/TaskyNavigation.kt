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
import com.example.tasky.agenda.presentation.edit.model.EditType
import com.example.tasky.agenda.presentation.edit.viewmodel.EditEvent
import com.example.tasky.agenda.presentation.edit.viewmodel.EditViewModel
import com.example.tasky.agenda.presentation.home.AgendaScreen
import com.example.tasky.agenda.presentation.home.viewmodel.AgendaEvent
import com.example.tasky.agenda.presentation.home.viewmodel.AgendaViewModel
import com.example.tasky.agenda.presentation.itemdetail.EventDetailScreen
import com.example.tasky.agenda.presentation.itemdetail.viewmodel.EventDetailEvent
import com.example.tasky.agenda.presentation.itemdetail.viewmodel.EventDetailViewModel
import com.example.tasky.agenda.presentation.photoviewer.PhotoViewerEvent
import com.example.tasky.agenda.presentation.photoviewer.PhotoViewerScreen
import com.example.tasky.agenda.presentation.photoviewer.PhotoViewerViewModel
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
                    viewModel.onEvent(AgendaEvent.ErrorHandled)
                }
                ObserveBoolean(isLoggedOut) {
                    onLogout()
                    navController.navigate(TaskyRoutes.LoginScreen.route) {
                        popUpTo(TaskyRoutes.AgendaScreen.route) {
                            inclusive = true
                        }
                    }
                    viewModel.onEvent(AgendaEvent.LogoutHandled)
                }
                Observe(navigateToEventDetail) { eventId ->
                    navController.navigate(TaskyRoutes.EventDetailScreen.getDestination(eventId = eventId))
                    viewModel.onEvent(AgendaEvent.EventNavigationHandled)
                }
                Observe(navigateToNewEvent) { date ->
                    navController.navigate(TaskyRoutes.EventDetailScreen.getDestination(date = date))
                    viewModel.onEvent(AgendaEvent.NewItemHandled)
                }
            }
            AgendaScreen(state = viewModel.state, onEvent = viewModel::onEvent)
        }

        composable(
            route = TaskyRoutes.EventDetailScreen.getCompleteRoute(),
            arguments = listOf(
                navArgument(TaskyRoutes.EventDetailScreen.EVENT_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(TaskyRoutes.EventDetailScreen.DATE) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) { entry ->
            val viewModel: EventDetailViewModel = hiltViewModel()

            viewModel.setEditedText(entry.savedStateHandle)

            viewModel.deletePhoto(entry.savedStateHandle)

            with(viewModel.state) {
                ObserveBoolean(navigateBack) {
                    navController.popBackStack()
                    viewModel.onEvent(EventDetailEvent.CloseClickResolved)
                }

                Observe(observer = navigateEditTitle) {
                    navController.navigate(
                        TaskyRoutes.EditScreen.getDestination(
                            text = it,
                            editType = EditType.Title
                        )
                    )
                    viewModel.onEvent(EventDetailEvent.NavigateEditTitleResolved)
                }

                Observe(observer = navigateEditDescription) {
                    navController.navigate(
                        TaskyRoutes.EditScreen.getDestination(
                            text = it,
                            editType = EditType.Description
                        )
                    )
                    viewModel.onEvent(EventDetailEvent.NavigateEditDescriptionResolved)
                }

                Observe(observer = navigatePhotoViewer) {
                    navController.navigate(
                        TaskyRoutes.PhotoViewerScreen.getDestination(
                            imageUrl = it
                        )
                    )
                    viewModel.onEvent(EventDetailEvent.PhotoClickedResolved)
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
                navArgument(TaskyRoutes.EditScreen.EDIT_TYPE) {
                    type = NavType.inferFromValueType(EditType.Title)
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
                        ?.set(TaskyRoutes.EditScreen.EDIT_TYPE, editType)
                    navController.popBackStack()
                    viewModel.onEvent(EditEvent.OnSaveResolved)
                }
            }

            EditScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent
            )
        }

        composable(
            route = TaskyRoutes.PhotoViewerScreen.getCompleteRoute(),
            arguments = listOf(
                navArgument(TaskyRoutes.PhotoViewerScreen.IMAGE_URL) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: PhotoViewerViewModel = hiltViewModel()

            with(viewModel.state) {
                Observe(observer = navigateBack) {
                    navController.popBackStack()
                    viewModel.onEvent(PhotoViewerEvent.OnBackResolved)
                }

                Observe(observer = navigateWithDelete) { imageUrl ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(TaskyRoutes.PhotoViewerScreen.IMAGE_URL, imageUrl)
                    navController.popBackStack()
                    viewModel.onEvent(PhotoViewerEvent.OnDeleteResolved)
                }
            }

            PhotoViewerScreen(imageUrl = viewModel.state.imageUrl, onEvent = viewModel::onEvent)
        }
    }
}