@file:SuppressLint("NewApi")

package com.example.tasky.agenda.domain.util

import android.annotation.SuppressLint
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toCurrentTime(): String {
    val time = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )

    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm")
    return time.format(dateFormatter)
}

fun Long.toCurrentDate(): String {
    val time = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM y")
    return time.format(dateFormatter)
}

fun Long.toHours(): String {
    val time = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )

    val dateFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return time.format(dateFormatter)
}

fun Long.toSimplifiedDate(): String {
    val time = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )

    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd y")
    return time.format(dateFormatter)
}

fun Long.toLocalDate(): LocalDateTime {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
}


fun LocalDate.toStartOfDayLong() =
    this.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDate.toEndOfDayLong() =
    this.atStartOfDay().plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDate.toCurrentTimeMilli() =
    LocalDateTime.of(this, LocalTime.now()).atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()