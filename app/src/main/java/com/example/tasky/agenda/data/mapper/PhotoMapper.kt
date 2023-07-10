package com.example.tasky.agenda.data.mapper

import com.example.tasky.agenda.data.remote.model.PhotoResponse
import com.example.tasky.agenda.domain.model.AgendaPhoto

fun PhotoResponse.toDomain() =
    AgendaPhoto.Remote(url = url)