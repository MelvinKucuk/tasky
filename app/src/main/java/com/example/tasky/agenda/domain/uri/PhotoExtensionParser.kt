package com.example.tasky.agenda.domain.uri

import android.net.Uri

interface PhotoExtensionParser {
    suspend fun extensionFromUri(uri: Uri): String?
}