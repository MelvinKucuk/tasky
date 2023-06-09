@file:SuppressLint("NewApi")
package com.example.tasky.agenda.data.util

import android.annotation.SuppressLint
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

fun Long.toCurrentTime(): String {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    ).toLocalTime().toString()
}


fun LocalDate.asTodayStartLong() =
    this.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDate.asTodayEndLong() =
    this.atStartOfDay().plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDate.toCurrentTimeMilli() =
    LocalDateTime.of(this, LocalTime.now()).atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()