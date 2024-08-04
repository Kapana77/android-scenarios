package com.example.foroom.domain.repository.local

import com.example.foroom.domain.model.User

interface ForoomLocalRepository {

    fun setCurrentUser(user: User)

    fun getCurrentUser(): User?

    fun removeCurrentUser()

}