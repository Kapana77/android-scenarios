package com.example.foroom.data.repository.local

import com.example.foroom.data.datasource.local.ForoomUserInMemoryDataSource
import com.example.foroom.data.mapper.ForoomMapper
import com.example.foroom.domain.model.User
import com.example.foroom.domain.repository.local.ForoomLocalRepository

class ForoomLocalRepositoryImpl(
    private val dataSource: ForoomUserInMemoryDataSource,
    private val mapper: ForoomMapper
) : ForoomLocalRepository {

    override fun getCurrentUser(): User? = dataSource.getCurrentUser()?.let { userEntity ->
        mapper.mapFromUserEntity(userEntity)
    }

    override fun setCurrentUser(user: User) =
        dataSource.setCurrentUser(mapper.mapToUserEntity(user))

    override fun removeCurrentUser() = dataSource.removeCurrentUser()

}