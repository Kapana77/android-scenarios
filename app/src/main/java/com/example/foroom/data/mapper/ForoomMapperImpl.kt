package com.example.foroom.data.mapper

import com.example.foroom.data.model.ChatEntity
import com.example.foroom.data.model.Image
import com.example.foroom.data.model.MessageEntity
import com.example.foroom.data.model.UserEntity
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import com.example.foroom.data.model.request.SendMessageRequestEntity
import com.example.foroom.data.model.response.ChatsResponseEntity
import com.example.foroom.domain.model.Chat
import com.example.foroom.domain.model.response.ChatsResponse
import com.example.foroom.domain.model.request.LogInRequest
import com.example.foroom.domain.model.Message
import com.example.foroom.domain.model.User
import com.example.foroom.domain.model.request.RegistrationRequest
import com.example.foroom.domain.model.request.SendMessageRequest
import java.time.ZonedDateTime

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

    private fun mapToChat(chatEntity: ChatEntity): Chat {
        return with(chatEntity) {
            Chat(name, emojiUrl, creatorUsername, likeCount, id)
        }
    }

    override fun mapToMessage(messageEntity: MessageEntity): Message {
        return with(messageEntity) {
            Message(
                username,
                avatarUrl,
                ZonedDateTime.parse(createdAt).toLocalDateTime(),
                text,
                userId,
                id
            )
        }
    }

    override fun mapToSendMessageRequest(request: SendMessageRequest): SendMessageRequestEntity {
        return with(request) {
            SendMessageRequestEntity(userId, chatId, text)
        }
    }
}
