package com.biblublab.album.common

import android.app.Application
import com.biblublab.album.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AlbumApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AlbumApplication)
            modules(dbModule,
                networkModule,
                dispatchersModule,
                useCaseModule,
                albumModule,
            )
        }
    }
}