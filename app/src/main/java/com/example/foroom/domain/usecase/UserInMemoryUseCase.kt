package com.example.foroom.domain.usecase

import com.example.foroom.domain.model.User
import com.example.foroom.domain.repository.local.ForoomLocalRepository

class UserInMemoryUseCase(private val repository: ForoomLocalRepository) {

    fun setCurrentUser(user: User) = repository.setCurrentUser(user)

    fun getCurrentUser(): User? = repository.getCurrentUser()

    fun removeCurrentUser() = repository.removeCurrentUser()
}