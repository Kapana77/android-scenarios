package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.web_socket.SignalrWebSocketClient
import com.example.network.web_socket.SignalrWebSocketClientImpl
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

    factory<SignalrWebSocketClient>(named(SignalrWebSocketClientImpl.ForoomHub.CHAT)) {
        SignalrWebSocketClientImpl(SignalrWebSocketClientImpl.ForoomHub.CHAT)
    }
}