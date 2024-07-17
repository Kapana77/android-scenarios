package com.example.network.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("http://89.168.75.214/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}