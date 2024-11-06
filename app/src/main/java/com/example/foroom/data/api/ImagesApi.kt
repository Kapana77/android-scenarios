package com.example.foroom.data.api

import com.example.foroom.data.model.Image
import retrofit2.http.GET

interface ImagesApi {

    @GET("api/avatars")
    suspend fun getAvatars(): List<Image>

    @GET("api/emojis")
    suspend fun getEmojis(): List<Image>

}