package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.auth.UserTokenRuntimeHolder
import com.example.network.auth.UserTokenRuntimeHolderImpl
import com.example.network.web_socket.ForoomWebSocketClient
import com.example.network.web_socket.ForoomWebSocketClientImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<UserTokenRuntimeHolder> {
        UserTokenRuntimeHolderImpl()
    }

    single<Retrofit> {
        val newClientBuilder = OkHttpClient.Builder()

        newClientBuilder.addInterceptor { chain ->
            val userToken = get<UserTokenRuntimeHolder>().getUserToken()

            val newRequest = chain
                .request()
                .newBuilder()
                .addHeader(UserTokenRuntimeHolder.BEARER_TOKEN_KEY, userToken)
                .build()

            chain.proceed(newRequest)
        }
        newClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(newClientBuilder.build())
            .build()
    }

    factory<ForoomWebSocketClient>(named(ForoomWebSocketClientImpl.ForoomHub.CHAT)) {
        ForoomWebSocketClientImpl(ForoomWebSocketClientImpl.ForoomHub.CHAT)
    }
}