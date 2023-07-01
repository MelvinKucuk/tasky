package com.example.tasky

sealed class TaskyRoutes(val route: String) {
    object LoginScreen : TaskyRoutes("login_screen")
    object SignUpScreen : TaskyRoutes("sign_up_screen")
    object AgendaScreen : TaskyRoutes("agenda_screen")
    object EventDetailScreen : TaskyRoutes("event_detail_screen") {
        fun getCompleteRoute() = "$route?$EVENT_ID={$EVENT_ID}"
        fun getDestination(eventId: String) = "$route?$EVENT_ID=$eventId"

        const val EVENT_ID = "event_id"
    }
}
