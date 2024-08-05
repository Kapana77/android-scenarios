package com.example.foroom.data.datasource.rest

import com.example.foroom.data.api.ImagesApi
import com.example.foroom.data.api.ForoomApi
import com.example.foroom.data.model.Image
import com.example.foroom.data.model.UserEntity
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import com.example.foroom.data.model.response.ChatsResponseEntity

class ForoomRestDataSourceImpl(
    private val imagesApi: ImagesApi,
    private val foroomApi: ForoomApi
) : ForoomRestDataSource {

    override suspend fun getCurrentUser(): UserEntity {
        return foroomApi.getCurrentUser()
    }

    override suspend fun getAvatars(): List<Image> {
        return imagesApi.getAvatars()
    }

    override suspend fun getEmojis(): List<Image> {
        return imagesApi.getEmojis()
    }

    override suspend fun registerUser(request: RegistrationRequestEntity) {
        return foroomApi.registerUser(request)
    }

    override suspend fun logInUser(request: LogInRequestEntity) {
        return foroomApi.logInUser(request)
    }

    override suspend fun getChats(
        page: Int,
        limit: Int,
        name: String?,
        popular: Boolean,
        created: Boolean,
        favorite: Boolean
    ): ChatsResponseEntity {
        return foroomApi.getChats(page, limit, name, popular, created, favorite)
    }
}