package com.example.tasky.agenda.domain

import com.example.tasky.authentication.domain.UserCache
import javax.inject.Inject

class GetInitialsUseCase @Inject constructor(
    private val userCache: UserCache
) {

    operator fun invoke(): String {
        val name = userCache.getUser()?.fullName ?: return ""
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