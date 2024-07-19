package com.example.foroom.data.datasource

import com.example.foroom.data.api.ImagesApi
import com.example.foroom.data.api.ForoomApi
import com.example.foroom.data.model.Image
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import com.example.foroom.data.model.response.ChatsResponseEntity

class ForoomDataSourceImpl(
    private val imagesApi: ImagesApi,
    private val foroomApi: ForoomApi
): ForoomDataSource {
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

    override suspend fun getChats(): ChatsResponseEntity {
        return foroomApi.getChats()
    }
}