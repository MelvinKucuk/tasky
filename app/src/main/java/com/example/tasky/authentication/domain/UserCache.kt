package com.example.tasky.authentication.domain

import com.example.tasky.authentication.domain.model.User

interface UserCache {
    fun saveUser(user: User)
    fun getUser(): User?
    fun deleteUser()
}