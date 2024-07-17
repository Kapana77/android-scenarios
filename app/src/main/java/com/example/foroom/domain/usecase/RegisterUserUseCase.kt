package com.example.foroom.domain.usecase

import com.example.foroom.domain.model.RegistrationRequest
import com.example.foroom.domain.repository.ForoomRepository

class RegisterUserUseCase(private val repository: ForoomRepository) {

    suspend operator fun invoke(request: RegistrationRequest) = repository.registerUser(request)

}