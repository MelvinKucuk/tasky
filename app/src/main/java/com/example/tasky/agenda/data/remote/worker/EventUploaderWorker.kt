package com.example.tasky.agenda.data.remote.worker

import android.content.Context
import android.net.Uri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tasky.agenda.data.remote.AgendaService
import com.example.tasky.agenda.domain.uri.PhotoByteConverter
import com.example.tasky.agenda.domain.uri.PhotoExtensionParser
import com.example.tasky.core.data.Resource
import com.example.tasky.core.data.remote.safeApiCall
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.UUID

@HiltWorker
class EventUploaderWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val agendaService: AgendaService,
    private val photoExtensionParser: PhotoExtensionParser,
    private val photoByteConverter: PhotoByteConverter,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val eventJson = workerParameters.inputData.getString(EVENT_JSON) ?: return Result.failure()
        val photos = workerParameters.inputData.getStringArray(EVENT_PHOTO_ARRAY) ?: emptyArray()

        val photoFiles = locationToMultipart(photos.toList())

        return when (uploadEvent(eventJson, photoFiles)) {
            is Resource.Success -> Result.success()
            is Resource.Error -> {
                if (runAttemptCount > 3) {
                    Result.failure()
                } else {
                    Result.retry()
                }
            }
        }
    }

    private suspend fun locationToMultipart(photos: List<String>): List<MultipartBody.Part> {
        return photos.mapIndexed { index, photoLocation ->
            val uri = Uri.parse(photoLocation)
            val extension = photoExtensionParser.extensionFromUri(uri)
            val uriBytes = photoByteConverter.uriToBytes(uri)

            MultipartBody.Part.createFormData(
                "photo$index",
                filename = UUID.randomUUID().toString() + "." + extension,
                uriBytes.toRequestBody()
            )
        }
    }

    private suspend fun uploadEvent(
        json: String,
        photos: List<MultipartBody.Part>,
    ) = safeApiCall {
        agendaService.createEvent(
            body = MultipartBody.Part.createFormData("create_event_request", json),
            files = photos
        )
    }

    companion object {
        const val EVENT_JSON = "event_json"
        const val EVENT_PHOTO_ARRAY = "event_photo_array"
    }
}