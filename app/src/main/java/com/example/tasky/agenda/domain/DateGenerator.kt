package com.example.tasky.agenda.domain

import com.example.tasky.agenda.domain.model.Day
import java.time.LocalDate

interface DateGenerator {
    fun getWeek(date: LocalDate = LocalDate.now()): List<Day>
    fun getMonth(): String
}