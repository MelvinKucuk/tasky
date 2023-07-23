package com.example.tasky.agenda.data.remote.uploader

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.tasky.agenda.data.mapper.toCreateDto
import com.example.tasky.agenda.data.remote.model.CreateEventDto
import com.example.tasky.agenda.data.remote.worker.EventUploaderWorker
import com.example.tasky.agenda.domain.EventUploader
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.domain.model.AgendaPhoto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.Duration
import javax.inject.Inject

class EventUploaderImpl @Inject constructor(
    private val workManager: WorkManager
) : EventUploader {

    override suspend fun uploadEvent(event: AgendaItem.Event) {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val jsonAdapter = moshi.adapter(CreateEventDto::class.java)
        val json: String = jsonAdapter.toJson(event.toCreateDto())

        val photoLocations = event.photos
            .filterIsInstance(AgendaPhoto.Local::class.java).map { it.url }
        val uploaderWorker = OneTimeWorkRequestBuilder<EventUploaderWorker>()
            .setConstraints(
                Constraints(requiredNetworkType = NetworkType.CONNECTED)
            )
            .setInputData(
                workDataOf(
                    EventUploaderWorker.EVENT_JSON to json,
                    EventUploaderWorker.EVENT_PHOTO_ARRAY to photoLocations.toTypedArray()
                )
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(1))
            .build()

        workManager.beginUniqueWork(
            "event_uploader",
            ExistingWorkPolicy.KEEP,
            uploaderWorker
        ).enqueue()
    }
}