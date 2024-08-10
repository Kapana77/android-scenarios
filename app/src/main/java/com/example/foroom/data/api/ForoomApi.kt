package com.example.foroom.data.api

import com.example.foroom.data.model.UserEntity
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import com.example.foroom.data.model.response.ChatsResponseEntity
import com.example.network.model.response.UserTokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ForoomApi {
    @GET("/users/currentUser")
    suspend fun getCurrentUser(): UserEntity

    @POST("/users/register")
    suspend fun registerUser(@Body request: RegistrationRequestEntity): UserTokenResponse

    @POST("/users/signin")
    suspend fun logInUser(@Body request: LogInRequestEntity): UserTokenResponse

    @GET("/chats")
    suspend fun getChats(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("name") name: String? = null,
        @Query("popular") popular: Boolean = false,
        @Query("created") created: Boolean = false,
        @Query("favorite") favorite: Boolean = false
    ): ChatsResponseEntity
}