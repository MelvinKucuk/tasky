package com.example.tasky.agenda.domain

import javax.inject.Inject

class GetInitialsUseCase @Inject constructor() {

    operator fun invoke(name: String?): String {
        if (name.isNullOrEmpty()) return ""
        val names = name.split(" ")
        val initials = names.map {
            if (names.size == 1) {
                it.first().uppercase() + it[1].uppercase()
            } else {
                it.first().uppercase()
            }
        }
        return initials.reduce { accumulated, initial -> accumulated + initial }
    }
}