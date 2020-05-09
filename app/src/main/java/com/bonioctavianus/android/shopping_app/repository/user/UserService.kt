package com.bonioctavianus.android.shopping_app.repository.user

interface UserService {
    fun saveUserId(userId: String)
    fun isUserSignedIn(): Boolean
    fun clearUser()
}