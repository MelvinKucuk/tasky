package com.example.tasky.agenda.presentation.model

data class Day(
    val letter: String,
    val number: String,
    val isSelected: Boolean = false
)
