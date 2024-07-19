package com.example.foroom.data.datasource

import com.example.foroom.data.model.Image
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import com.example.foroom.data.model.response.ChatsResponseEntity

interface ForoomDataSource {

    suspend fun getAvatars(): List<Image>

    suspend fun getEmojis(): List<Image>

    suspend fun registerUser(request: RegistrationRequestEntity)

    suspend fun logInUser(request: LogInRequestEntity)

    suspend fun getChats(): ChatsResponseEntity
}