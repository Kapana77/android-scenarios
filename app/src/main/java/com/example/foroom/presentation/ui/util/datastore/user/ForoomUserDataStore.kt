package com.example.foroom.presentation.ui.util.datastore.user

import com.example.foroom.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ForoomUserDataStore {

    suspend fun saveUser(user: User)

    suspend fun getUser(): Flow<User>

    suspend fun saveUserAuthToken(token: String)

    suspend fun getUserAuthToken(): Flow<String>

    suspend fun saveUserLanguage(language: String)

    suspend fun getUserLanguage(): String?

    suspend fun clearUserData()
}