package com.example.foroom.data.repository.rest

import com.example.foroom.data.datasource.rest.ForoomRestDataSource
import com.example.foroom.data.mapper.ForoomMapper
import com.example.foroom.domain.model.User
import com.example.foroom.domain.model.response.ChatsResponse
import com.example.foroom.domain.model.request.LogInRequest
import com.example.foroom.domain.model.request.RegistrationRequest
import com.example.foroom.domain.repository.rest.ForoomRestRepository
import com.example.network.model.response.UserTokenResponse
import com.example.shared.model.Image

class ForoomRestRepositoryImpl(
    private val dataSource: ForoomRestDataSource,
    private val mapper: ForoomMapper
) : ForoomRestRepository {

    override suspend fun getCurrentUser(): User {
        return mapper.mapFromUserEntity(dataSource.getCurrentUser())
    }

    override suspend fun getAvatars(): List<Image> {
        return dataSource.getAvatars().map { image ->
            mapper.mapImage(image)
        }
    }

    override suspend fun getEmojis(): List<Image> {
        return dataSource.getEmojis().map { image ->
            mapper.mapImage(image)
        }
    }

    override suspend fun registerUser(request: RegistrationRequest): UserTokenResponse {
        return dataSource.registerUser(
            mapper.mapToRegistrationRequest(request)
        )
    }

    override suspend fun logInUser(request: LogInRequest): UserTokenResponse {
        return dataSource.logInUser(
            mapper.mapToLogInRequest(request)
        )
    }

    override suspend fun getChats(
        page: Int,
        limit: Int,
        name: String?,
        popular: Boolean,
        created: Boolean,
        favorite: Boolean
    ): ChatsResponse {
        return mapper.mapToChatsResponse(
            dataSource.getChats(
                page,
                limit,
                name,
                popular,
                created,
                favorite
            )
        )
    }
}