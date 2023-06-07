package com.example.tasky.agenda.domain.model

data class Day(
    val letter: String,
    val number: String,
    val isSelected: Boolean = false
)
