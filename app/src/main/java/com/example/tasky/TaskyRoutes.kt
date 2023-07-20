package com.example.tasky

import com.example.tasky.agenda.presentation.edit.model.EditType
import java.net.URLEncoder

sealed class TaskyRoutes(val route: String) {
    object LoginScreen : TaskyRoutes("login_screen")
    object SignUpScreen : TaskyRoutes("sign_up_screen")
    object AgendaScreen : TaskyRoutes("agenda_screen")
    object EventDetailScreen : TaskyRoutes("event_detail_screen") {
        fun getCompleteRoute() = "$route?$EVENT_ID={$EVENT_ID}&$DATE={$DATE}"
        fun getDestination(eventId: String) = "$route?$EVENT_ID=$eventId"
        fun getDestination(date: Long) = "$route?$DATE=$date"

        const val EVENT_ID = "event_id"
        const val DATE = "date"
    }

    object EditScreen : TaskyRoutes("edit_screen") {
        fun getCompleteRoute() = "$route?$TEXT={$TEXT}&$EDIT_TYPE={$EDIT_TYPE}"
        fun getDestination(
            text: String,
            editType: EditType
        ) = "$route?$TEXT=$text&$EDIT_TYPE=$editType"

        const val TEXT = "event_id"
        const val EDIT_TYPE = "edit_type"
    }

    object PhotoViewerScreen : TaskyRoutes("photo_viewer_screen") {
        fun getCompleteRoute() = "$route?$IMAGE_URL={$IMAGE_URL}"
        fun getDestination(imageUrl: String): String {
            val encodedUrl = URLEncoder.encode(imageUrl, "UTF-8")
            return "$route?$IMAGE_URL=$encodedUrl"
        }

        const val IMAGE_URL = "image_url"
    }
}
