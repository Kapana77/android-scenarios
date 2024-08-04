package com.example.foroom.data.datasource.local

import com.example.foroom.data.model.UserEntity

class ForoomUserInMemoryDataSourceImpl: ForoomUserInMemoryDataSource {
    private var currentUser: UserEntity? = null

    override fun setCurrentUser(user: UserEntity) {
        currentUser = user
    }

    override fun getCurrentUser(): UserEntity? = currentUser

    override fun removeCurrentUser() {
        currentUser = null
    }
}