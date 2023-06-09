package com.example.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.tasky.authentication.presentation.login.LoginScreen
import com.example.tasky.authentication.presentation.login.viewmodel.LoginState
import com.example.tasky.core.presentation.SplashViewModel
import com.example.tasky.ui.theme.TaskyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashViewModel: SplashViewModel by viewModels()

        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.state.isLoading
        }
        splashViewModel.checkAuthentication()
        setContent {
            if (!splashViewModel.state.isLoading) {
                TaskyTheme {
                    val navController = rememberNavController()
                    TaskyNavigation(
                        navController = navController,
                        startDestination = if (splashViewModel.state.navigateToAgenda == true) {
                            TaskyRoutes.AgendaScreen.route
                        } else {
                            TaskyRoutes.LoginScreen.route
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskyTheme {
        LoginScreen(LoginState()) {}
    }
}