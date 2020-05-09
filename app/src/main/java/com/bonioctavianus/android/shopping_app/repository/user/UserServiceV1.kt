package com.bonioctavianus.android.shopping_app.repository.user

import com.bonioctavianus.android.shopping_app.store.ShopStore

class UserServiceV1(private val mStore: ShopStore) : UserService {

    companion object {
        private const val KEY_USER_ID = "user_id"
    }

    override fun saveUserId(userId: String) {
        mStore.putString(KEY_USER_ID, userId)
    }

    override fun isUserSignedIn(): Boolean {
        return mStore.getString(KEY_USER_ID, "").isNotEmpty()
    }

    override fun clearUser() {
        mStore.putString(KEY_USER_ID, "")
    }
}