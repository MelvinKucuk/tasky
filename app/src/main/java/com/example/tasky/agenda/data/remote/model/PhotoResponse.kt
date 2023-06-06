package com.example.tasky.agenda.data.remote.model

import com.squareup.moshi.Json

data class PhotoResponse(
    @Json(name = "key")
    val key: String,
    @Json(name = "url")
    val url: String
)
