package com.example.foroom.domain.repository.rest

import com.example.foroom.domain.model.User
import com.example.foroom.domain.model.response.ChatsResponse
import com.example.foroom.domain.model.request.LogInRequest
import com.example.foroom.domain.model.request.RegistrationRequest
import com.example.shared.model.Image

interface ForoomRestRepository {
    suspend fun getCurrentUser(): User

    suspend fun getAvatars(): List<Image>

    suspend fun getEmojis(): List<Image>

    suspend fun registerUser(request: RegistrationRequest)

    suspend fun logInUser(request: LogInRequest)

    suspend fun getChats(page: Int, limit: Int): ChatsResponse
}