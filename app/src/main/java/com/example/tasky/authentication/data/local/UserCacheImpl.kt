package com.example.tasky.authentication.data.local

import android.content.SharedPreferences
import com.example.tasky.authentication.domain.UserCache
import com.example.tasky.authentication.domain.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCacheImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserCache {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val adapter = moshi.adapter(User::class.java)
    override fun saveUser(user: User) {
        sharedPreferences.edit()
            .putString(userKey, adapter.toJson(user))
            .apply()
    }

    override fun getUser(): User? {
        val json = sharedPreferences.getString(userKey, null) ?: return null
        return adapter.fromJson(json)
    }

    override fun deleteUser() {
        sharedPreferences.edit().remove(userKey).apply()
    }

    companion object {
        private const val userKey = "user"
    }
}