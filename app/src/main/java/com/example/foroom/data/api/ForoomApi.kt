package com.example.foroom.data.api

import com.example.foroom.data.model.ChatEntity
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ForoomApi {

    @POST("/users/register")
    suspend fun registerUser(@Body request: RegistrationRequestEntity)

    @POST("/users/signin")
    suspend fun logInUser(@Body request: LogInRequestEntity)

    @GET("/chats")
    suspend fun getChats(): List<ChatEntity>
}