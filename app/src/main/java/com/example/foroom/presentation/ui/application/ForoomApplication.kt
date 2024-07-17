package com.example.foroom.presentation.ui.application

import android.app.Application
import com.example.foroom.di.appModule
import com.example.foroom.di.dataModule
import com.example.foroom.di.domainModule
import com.example.foroom.presentation.ui.di.presentationModule
import com.example.network.di.networkModule
import com.example.shared.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ForoomApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ForoomApplication)
            modules(appModule, dataModule, domainModule, sharedModule, networkModule, presentationModule)
        }
    }

}