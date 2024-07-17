package com.example.foroom.data.mapper

import com.example.foroom.data.model.ChatEntity
import com.example.foroom.data.model.Image
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import com.example.foroom.domain.model.Chat
import com.example.foroom.domain.model.LogInRequest

class ForoomMapperImpl: ForoomMapper {

    override fun mapImage(image: Image): com.example.shared.model.Image {
        return com.example.shared.model.Image(
            image.id,
            image.url
        )
    }

    override fun mapToRegistrationRequest(request: com.example.foroom.domain.model.RegistrationRequest): RegistrationRequestEntity {
        return RegistrationRequestEntity(request.userName, request.password, request.avatarId)
    }

    override fun mapToLogInRequest(request: LogInRequest): LogInRequestEntity {
        return LogInRequestEntity(request.userName, request.password)
    }

    override fun mapToChats(chatEntities: List<ChatEntity>): List<Chat> {
        return chatEntities.map { entity -> mapToChat(entity) }
    }

    private fun mapToChat(chatEntity: ChatEntity): Chat {
        return with(chatEntity) {
            Chat(name, emojiUrl, creatorUsername, likeCount, id)
        }
    }
}
