package com.example.countryinfo

import android.app.Application
import com.example.countryinfo.di.repoModule
import com.example.countryinfo.di.serviceModule
import com.example.countryinfo.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

/**
 * Created by Nitin Dasari on 10/5/22.
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                uiModule + repoModule + serviceModule
            )
        }
    }
}