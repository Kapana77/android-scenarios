package com.example.foroom.domain.repository

import com.example.foroom.domain.model.ChatsResponse
import com.example.foroom.domain.model.LogInRequest
import com.example.foroom.domain.model.RegistrationRequest
import com.example.shared.model.Image

interface ForoomRepository {

    suspend fun getAvatars(): List<Image>

    suspend fun getEmojis(): List<Image>

    suspend fun registerUser(request: RegistrationRequest)

    suspend fun logInUser(request: LogInRequest)

    suspend fun getChats(page: Int, limit: Int): ChatsResponse
}