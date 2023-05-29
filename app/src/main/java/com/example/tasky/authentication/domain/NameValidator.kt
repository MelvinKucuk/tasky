package com.example.tasky.authentication.domain

import javax.inject.Inject

class NameValidator @Inject constructor() {

    fun validateName(name: String) =
        name.length in (4..50)
}