package com.example.foroom.di

import com.example.foroom.data.api.ForoomApi
import com.example.foroom.data.api.ImagesApi
import com.example.foroom.data.datasource.rest.ForoomRestDataSource
import com.example.foroom.data.datasource.rest.ForoomRestDataSourceImpl
import com.example.foroom.data.datasource.web_socket.MessagesWebSocketDataSource
import com.example.foroom.data.datasource.web_socket.MessagesWebSocketDataSourceImpl
import com.example.foroom.data.mapper.ForoomMapper
import com.example.foroom.data.mapper.ForoomMapperImpl
import com.example.foroom.data.repository.rest.ForoomRestRepositoryImpl
import com.example.foroom.data.repository.web_socket.ForoomMessagesWebSocketRepositoryImpl
import com.example.foroom.domain.repository.rest.ForoomRestRepository
import com.example.foroom.domain.repository.web_socket.ForoomMessagesWebSocketRepository
import com.example.network.web_socket.ForoomWebSocketClientImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    // api
    single<ImagesApi> {
        get<Retrofit>().create(ImagesApi::class.java)
    }

    single<ForoomApi> {
        get<Retrofit>().create(ForoomApi::class.java)
    }

    // dataSource
    single<ForoomRestDataSource> {
        ForoomRestDataSourceImpl(get(), get())
    }

    single<MessagesWebSocketDataSource> {
        MessagesWebSocketDataSourceImpl(get(named(ForoomWebSocketClientImpl.ForoomHub.CHAT)))
    }

    // mapper
    single<ForoomMapper> {
        ForoomMapperImpl()
    }

    // repository
    single<ForoomRestRepository> {
        ForoomRestRepositoryImpl(get(), get())
    }

    single<ForoomMessagesWebSocketRepository> {
        ForoomMessagesWebSocketRepositoryImpl(get(), get())
    }
}