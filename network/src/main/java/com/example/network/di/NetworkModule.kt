package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.web_socket.ForoomWebSocketClient
import com.example.network.web_socket.ForoomWebSocketClientImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    val client = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    ).build()

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    factory<ForoomWebSocketClient>(named(ForoomWebSocketClientImpl.ForoomHub.CHAT)) {
        ForoomWebSocketClientImpl(ForoomWebSocketClientImpl.ForoomHub.CHAT)
    }
}