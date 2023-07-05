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

    object EditScreen : TaskyRoutes("edit_screen") {
        fun getCompleteRoute() = "$route?$TEXT={$TEXT}&$IS_TITLE={$IS_TITLE}"
        fun getDestination(
            text: String,
            isTitle: Boolean
        ) = "$route?$TEXT=$text&$IS_TITLE=$isTitle"

        const val TEXT = "event_id"
        const val IS_TITLE = "is_title"
    }
}
