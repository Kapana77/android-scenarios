package com.example.foroom.data.mapper

import com.example.foroom.data.model.Image
import com.example.foroom.data.model.request.LogInRequestEntity
import com.example.foroom.data.model.request.RegistrationRequestEntity
import com.example.foroom.data.model.response.ChatsResponseEntity
import com.example.foroom.domain.model.ChatsResponse
import com.example.foroom.domain.model.LogInRequest
import com.example.foroom.domain.model.RegistrationRequest

interface ForoomMapper {
    fun mapImage(image: Image): com.example.shared.model.Image

    fun mapToRegistrationRequest(request: RegistrationRequest): RegistrationRequestEntity

    fun mapToLogInRequest(request: LogInRequest): LogInRequestEntity

    fun mapToChatsResponse(responseEntity: ChatsResponseEntity): ChatsResponse
}