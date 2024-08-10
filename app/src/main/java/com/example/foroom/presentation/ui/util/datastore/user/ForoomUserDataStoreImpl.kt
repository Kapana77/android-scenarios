package com.example.foroom.presentation.ui.util.datastore.user

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.foroom.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ForoomUserDataStoreImpl(private val context: Context) : ForoomUserDataStore {

    private val Context.dataStore by preferencesDataStore(USER_PREFERENCES_NAME)

    private val userNameKey = stringPreferencesKey(USER_NAME_PREFERENCES_KEY)
    private val avatarUrlKey = stringPreferencesKey(AVATAR_URL_PREFERENCES_KEY)
    private val userIdKey = stringPreferencesKey(USER_ID_PREFERENCES_KEY)
    private val userTokenKey = stringPreferencesKey(USER_TOKEN_PREFERENCES_KEY)

    override suspend fun saveUser(user: User) {
        context.dataStore.edit { prefs ->
            prefs[userNameKey] = user.userName
            prefs[avatarUrlKey] = user.avatarUrl
            prefs[userIdKey] = user.id
        }
    }

    override suspend fun getUser(): Flow<User> = context.dataStore.data.map { data ->
        // Use Flow.catch to handle absent values

        User(
            id = data[userIdKey]!!,
            userName = data[userNameKey]!!,
            avatarUrl = data[avatarUrlKey]!!
        )
    }

    override suspend fun saveUserAuthToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[userTokenKey] = token
        }
    }

    override suspend fun getUserAuthToken(): Flow<String> = context.dataStore.data.map { data ->
        // Use Flow.catch to handle absent values

        data[userTokenKey]!!
    }

    override suspend fun clearUserData() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    companion object {
        private const val USER_PREFERENCES_NAME = "userPreferences"
        private const val USER_NAME_PREFERENCES_KEY = "userName"
        private const val AVATAR_URL_PREFERENCES_KEY = "avatarUrl"
        private const val USER_ID_PREFERENCES_KEY = "userId"
        private const val USER_TOKEN_PREFERENCES_KEY = "userToken"
    }
}