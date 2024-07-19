package com.example.foroom.data.repository

import com.example.foroom.data.datasource.ForoomDataSource
import com.example.foroom.data.mapper.ForoomMapper
import com.example.foroom.domain.model.ChatsResponse
import com.example.foroom.domain.model.LogInRequest
import com.example.foroom.domain.model.RegistrationRequest
import com.example.foroom.domain.repository.ForoomRepository
import com.example.shared.model.Image

class ForoomRepositoryImpl(
    private val dataSource: ForoomDataSource,
    private val mapper: ForoomMapper
) : ForoomRepository {

    override suspend fun getAvatars(): List<Image> {
        return dataSource.getAvatars().map {  image ->
            mapper.mapImage(image)
        }
    }

    override suspend fun getEmojis(): List<Image> {
        return dataSource.getEmojis().map {  image ->
            mapper.mapImage(image)
        }
    }

    override suspend fun registerUser(request: RegistrationRequest) {
        dataSource.registerUser(
            mapper.mapToRegistrationRequest(request)
        )
    }

    override suspend fun logInUser(request: LogInRequest) {
        dataSource.logInUser(
            mapper.mapToLogInRequest(request)
        )
    }

    override suspend fun getChats(): ChatsResponse {
        return mapper.mapToChatsResponse(dataSource.getChats())
    }
}