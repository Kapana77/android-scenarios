package com.example.foroom.data.datasource.rest

import com.example.foroom.data.model.Image
import com.example.foroom.data.model.UserEntity
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import com.example.foroom.data.model.response.ChatsResponseEntity
import com.example.network.model.response.UserTokenResponse

interface ForoomRestDataSource {
    suspend fun getCurrentUser(): UserEntity

    suspend fun getAvatars(): List<Image>

    suspend fun getEmojis(): List<Image>

    suspend fun registerUser(request: RegistrationRequestEntity): UserTokenResponse

    suspend fun logInUser(request: LogInRequestEntity): UserTokenResponse

    suspend fun getChats(
        page: Int,
        limit: Int,
        name: String? = null,
        popular: Boolean = false,
        created: Boolean = false,
        favorite: Boolean = false
    ): ChatsResponseEntity

}