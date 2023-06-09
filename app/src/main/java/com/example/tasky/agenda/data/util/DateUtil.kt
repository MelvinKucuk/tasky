package com.example.tasky.agenda.data.util

import android.annotation.SuppressLint
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@SuppressLint("NewApi")
fun Long.toCurrentTime(): String {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    ).toLocalTime().toString()
}