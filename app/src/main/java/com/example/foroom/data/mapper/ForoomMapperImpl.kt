package com.example.foroom.data.mapper

import com.example.foroom.data.model.ChatEntity
import com.example.foroom.data.model.Image
import com.example.foroom.data.model.MessageEntity
import com.example.foroom.data.model.UserEntity
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import com.example.foroom.data.model.request.SendMessageRequestEntity
import com.example.foroom.data.model.response.ChatsResponseEntity
import com.example.foroom.data.model.response.MessageHistoryResponseEntity
import com.example.foroom.domain.model.Chat
import com.example.foroom.domain.model.response.ChatsResponse
import com.example.foroom.domain.model.request.LogInRequest
import com.example.foroom.domain.model.Message
import com.example.foroom.domain.model.User
import com.example.foroom.domain.model.request.RegistrationRequest
import com.example.foroom.domain.model.request.SendMessageRequest
import com.example.foroom.domain.model.response.MessageHistoryResponse
import java.time.LocalDateTime

class ForoomMapperImpl : ForoomMapper {

    override fun mapToUserEntity(user: User): UserEntity {
        return with(user) {
            UserEntity(id, userName, avatarUrl)
        }
    }

    override fun mapFromUserEntity(userEntity: UserEntity): User {
        return with(userEntity) {
            User(id, userName, avatarUrl)
        }
    }

    override fun mapImage(image: Image): com.example.shared.model.Image {
        return com.example.shared.model.Image(
            image.id,
            image.url
        )
    }

    override fun mapToRegistrationRequest(request: RegistrationRequest): RegistrationRequestEntity {
        return RegistrationRequestEntity(request.userName, request.password, request.avatarId)
    }

    override fun mapToLogInRequest(request: LogInRequest): LogInRequestEntity {
        return LogInRequestEntity(request.userName, request.password)
    }

    override fun mapToChatsResponse(responseEntity: ChatsResponseEntity): ChatsResponse {
        return with(responseEntity) {
            ChatsResponse(
                chats = result.map(::mapToChat),
                hasNext = hasNext
            )
        }
    }

    override fun mapToChat(chatEntity: ChatEntity): Chat {
        return with(chatEntity) {
            Chat(name, emojiUrl, creatorUsername, likeCount, id)
        }
    }

    override fun mapToMessage(messageEntity: MessageEntity, currentUserId: String): Message {
        return with(messageEntity) {
            Message(
                username,
                avatarUrl,
                LocalDateTime.now(),
                text,
                userId,
                id,
                isCurrentUser = userId == currentUserId
            )
        }
    }

    override fun mapToMessageHistoryResponse(
        responseEntity: MessageHistoryResponseEntity,
        currentUserId: String
    ): MessageHistoryResponse {
        return with(responseEntity) {
            MessageHistoryResponse(
                messages = result.map { entity -> mapToMessage(entity, currentUserId) },
                hasNext = hasNext
            )
        }
    }

    override fun mapToSendMessageRequest(request: SendMessageRequest): SendMessageRequestEntity {
        return with(request) {
            SendMessageRequestEntity(userId, chatId, text)
        }
    }
}
