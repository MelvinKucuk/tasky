package com.example.tasky.authentication.data.mapper

import com.example.tasky.authentication.data.remote.login.LoginResponse
import com.example.tasky.authentication.domain.model.User

fun LoginResponse.toUser() =
    User(
        token = this.token,
        userId = this.userId,
        fullName = this.fullName
    )