package com.example.foroom.data.datasource.local

import com.example.foroom.data.model.UserEntity

interface ForoomUserInMemoryDataSource {
    fun setCurrentUser(user: UserEntity)

    fun getCurrentUser(): UserEntity?

    fun removeCurrentUser()
}