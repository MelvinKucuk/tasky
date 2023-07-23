package com.example.tasky.agenda.domain.model

sealed class AgendaPhoto(
    val key: String,
    val url: String
) {
    class Local(url: String) : AgendaPhoto(key = "", url = url)
    class Remote(url: String, key: String) : AgendaPhoto(key = key, url = url)
}