package com.example.tasky.agenda.domain.uri

import android.net.Uri

interface PhotoByteConverter {
    suspend fun uriToBytes(uri: Uri): ByteArray
}