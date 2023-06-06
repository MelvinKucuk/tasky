package com.example.tasky.agenda.domain

import com.example.tasky.agenda.presentation.model.Day

interface DateGenerator {
    fun getWeek(): List<Day>
    fun getMonth(): String
}