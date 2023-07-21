package com.example.tasky.agenda.data.uri

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.tasky.agenda.domain.uri.PhotoByteConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.math.roundToInt

class PhotoByteConverterImpl @Inject constructor(
    private val context: Context,
) : PhotoByteConverter {

    override suspend fun uriToBytes(uri: Uri): ByteArray {
        return withContext(Dispatchers.IO) {
            val bitmap = bitmapFromUri(uri)

            var outputBytes: ByteArray
            var quality = 100
            do {
                val outputStream = ByteArrayOutputStream()
                outputStream.use {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                    outputBytes = outputStream.toByteArray()
                    quality -= (quality * 0.1).roundToInt()
                }
            } while (outputBytes.size > COMPRESSION_THRESHOLD && quality > MINIMUM_QUALITY)

            return@withContext outputBytes
        }
    }

    private suspend fun bitmapFromUri(uri: Uri): Bitmap {
        return withContext(Dispatchers.IO) {
            val bytes = context.contentResolver.openInputStream(uri)?.use {
                it.readBytes()
            } ?: byteArrayOf()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }

    companion object {
        const val COMPRESSION_THRESHOLD = 1024 * 1024L
        const val MINIMUM_QUALITY = 70
    }
}