package com.example.foroom.data.mapper

import com.example.foroom.data.model.Image
import com.example.foroom.data.model.MessageEntity
import com.example.foroom.data.model.UserEntity
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import com.example.foroom.data.model.request.SendMessageRequestEntity
import com.example.foroom.data.model.response.ChatsResponseEntity
import com.example.foroom.data.model.response.MessageHistoryResponseEntity
import com.example.foroom.domain.model.response.ChatsResponse
import com.example.foroom.domain.model.request.LogInRequest
import com.example.foroom.domain.model.Message
import com.example.foroom.domain.model.User
import com.example.foroom.domain.model.request.RegistrationRequest
import com.example.foroom.domain.model.request.SendMessageRequest
import com.example.foroom.domain.model.response.MessageHistoryResponse

interface ForoomMapper {
    fun mapFromUserEntity(userEntity: UserEntity): User

    fun mapToUserEntity(user: User): UserEntity

    fun mapImage(image: Image): com.example.shared.model.Image

    fun mapToRegistrationRequest(request: RegistrationRequest): RegistrationRequestEntity

    fun mapToLogInRequest(request: LogInRequest): LogInRequestEntity

    fun mapToChatsResponse(responseEntity: ChatsResponseEntity): ChatsResponse

    fun mapToMessage(messageEntity: MessageEntity, currentUserId: String): Message

    fun mapToMessageHistoryResponse(
        responseEntity: MessageHistoryResponseEntity,
        currentUserId: String
    ): MessageHistoryResponse

    fun mapToSendMessageRequest(request: SendMessageRequest): SendMessageRequestEntity
}