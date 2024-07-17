package com.example.foroom.di

import com.example.foroom.data.api.ImagesApi
import com.example.foroom.data.api.ForoomApi
import com.example.foroom.data.datasource.ForoomDataSource
import com.example.foroom.data.datasource.ForoomDataSourceImpl
import com.example.foroom.data.mapper.ForoomMapper
import com.example.foroom.data.mapper.ForoomMapperImpl
import com.example.foroom.data.repository.ForoomRepositoryImpl
import com.example.foroom.domain.repository.ForoomRepository
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
    single<ForoomDataSource> {
        ForoomDataSourceImpl(get(), get())
    }

    // mapper
    single<ForoomMapper> {
        ForoomMapperImpl()
    }

    // repository
    single<ForoomRepository> {
        ForoomRepositoryImpl(get(), get())
    }
}