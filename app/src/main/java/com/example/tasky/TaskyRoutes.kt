package com.example.tasky

sealed class TaskyRoutes(val route: String) {
    object LoginScreen : TaskyRoutes("login_screen")
    object SignUpScreen : TaskyRoutes("sign_up_screen")
    object AgendaScreen : TaskyRoutes("agenda_screen")
    object EventDetailScreen : TaskyRoutes("event_detail_screen")
}
