package com.example.tasky.agenda.domain.util

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

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun Long.toLocalTime(): LocalTime {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalTime()
}

fun LocalDate.toStartOfDayLong() =
    this.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDate.toEndOfDayLong() =
    this.atStartOfDay().plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDate.toCurrentTimeMilli() =
    LocalDateTime.of(this, LocalTime.now()).atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()

fun LocalDateTime.toLong() =
    this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()