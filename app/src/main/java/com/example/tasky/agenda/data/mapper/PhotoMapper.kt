package com.example.tasky.agenda.data.mapper

import com.example.tasky.agenda.data.remote.model.PhotoResponse
import com.example.tasky.agenda.domain.model.Photo

fun PhotoResponse.toDomain() =
    Photo(
        key = key,
        url = url
    )