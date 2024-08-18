package com.example.foroom.data.api

import com.example.foroom.data.model.UserEntity
import com.example.foroom.data.model.request.ChangePasswordRequest
import com.example.foroom.data.model.request.ChangeUserAvatarRequest
import com.example.foroom.data.model.request.ChangeUsernameRequest
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import com.example.foroom.data.model.response.ChatsResponseEntity
import com.example.foroom.data.model.response.MessageHistoryResponseEntity
import com.example.network.model.response.UserTokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @GET("/messages")
    suspend fun getMessageHistory(
        @Query("chatId") chatId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): MessageHistoryResponseEntity

    @PUT("/Users/ChangeAvatar")
    suspend fun changeAvatar(@Body request: ChangeUserAvatarRequest)

    @PUT("/Users/ChangeUsername")
    suspend fun changeUsername(@Body request: ChangeUsernameRequest)

    @PUT("/Users/ResetPassword")
    suspend fun changePassword(@Body request: ChangePasswordRequest)

    @POST("Users/SignOut")
    suspend fun signOut()
}