package com.example.tasky.agenda.domain

import android.annotation.SuppressLint
import com.example.tasky.agenda.domain.model.Day
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class DateGenerator @Inject constructor() {

    @SuppressLint("NewApi")
    fun getWeek(date: LocalDate = LocalDate.now()): List<Day> {
        val calendar = Calendar.getInstance()
        calendar.set(date.year, date.month.ordinal, date.dayOfMonth)

        val daysOfWeek = (1..7).map {
            val dayOfWeek =
                calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
                    ?.get(0)
                    ?.uppercase() ?: ""
            Day(
                letter = dayOfWeek,
                number = calendar.get(Calendar.DAY_OF_MONTH).toString(),
                isSelected = it == 1
            )
        }

        return daysOfWeek
    }

    fun getMonth(): String {
        val calendar = Calendar.getInstance()

        return calendar.getDisplayName(
            Calendar.MONTH,
            Calendar.LONG,
            Locale.getDefault()
        )?.uppercase() ?: ""
    }
}