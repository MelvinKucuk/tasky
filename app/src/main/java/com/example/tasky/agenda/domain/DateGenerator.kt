@file:SuppressLint("NewApi")

package com.example.tasky.agenda.domain

import android.annotation.SuppressLint
import com.example.tasky.agenda.domain.model.Day
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class DateGenerator @Inject constructor() {

    fun getWeek(date: LocalDate = LocalDate.now()): List<Day> {
        val calendar = Calendar.getInstance()
        calendar.set(date.year, date.month.ordinal, date.dayOfMonth)

        val daysOfWeek = (1..7).map {
            val letterOfDay =
                calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
                    ?.get(0)
                    ?.uppercase() ?: ""
            val numberOfDay = calendar.get(Calendar.DAY_OF_MONTH).toString()

            calendar.add(Calendar.DAY_OF_WEEK, 1)

            Day(
                letter = letterOfDay,
                number = numberOfDay,
                isSelected = it == 1
            )
        }

        return daysOfWeek
    }

    /*fun getMonth(date: LocalDate): String {
        val calendar = Calendar.getInstance()
        calendar.set(date.year, date.month.ordinal, date.dayOfMonth)

        return calendar.getDisplayName(
            Calendar.MONTH,
            Calendar.LONG,
            Locale.getDefault()
        )?.uppercase() ?: ""
    }*/

    fun getMonth(date: LocalDate) = date.month.name.uppercase()
}