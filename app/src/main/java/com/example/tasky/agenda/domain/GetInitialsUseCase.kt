package com.example.tasky.agenda.domain

import javax.inject.Inject

class GetInitialsUseCase @Inject constructor() {

    operator fun invoke(name: String?): String {
        if (name.isNullOrEmpty()) return ""
        val names = name.split(" ")

        val initials =
            when (names.size) {
                1 -> names.first()[0].uppercase() + names.first()[1].uppercase()
                else -> names.first()[0].uppercase() + names[names.lastIndex].first().uppercase()
            }

        return initials
    }
}