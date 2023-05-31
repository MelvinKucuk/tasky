package com.example.tasky.authentication.domain

import javax.inject.Inject

class NameValidator @Inject constructor() {

    operator fun invoke(name: String) =
        name.length in (4..50)
}