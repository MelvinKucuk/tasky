package com.example.tasky.agenda.data

import com.example.tasky.agenda.domain.DateGenerator
import com.example.tasky.agenda.presentation.model.Day
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Locale
import javax.inject.Inject

class DateGeneratorImpl @Inject constructor() : DateGenerator {

    override fun getWeek(): List<Day> {
        val calendar = Calendar.getInstance()
        val daysOfWeek = mutableListOf<Day>()

        for (i in 1..7) {
            val dayOfWeek =
                calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
                    ?.get(0)
                    ?.uppercase() ?: ""
            daysOfWeek.add(
                Day(
                    letter = dayOfWeek,
                    number = calendar.get(DAY_OF_MONTH).toString(),
                    isSelected = i == 1
                )
            )
            calendar.add(Calendar.DAY_OF_WEEK, 1)
        }

        return daysOfWeek
    }

    override fun getMonth(): String {
        val calendar = Calendar.getInstance()

        return calendar.getDisplayName(
            Calendar.MONTH,
            Calendar.LONG,
            Locale.getDefault()
        )?.uppercase() ?: ""
    }
}