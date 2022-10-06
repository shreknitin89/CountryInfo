package com.example.countryinfo.di

import com.example.countryinfo.presentation.CountryViewModel
import com.example.data.DispatcherProvider
import com.example.data.DispatcherProviderImpl
import com.example.data.repo.CountryRepoImpl
import com.example.data.service.CountryApiService
import com.example.domain.repo.CountryRepo
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Nitin Dasari on 10/5/22.
 */

val uiModule = module {
    viewModel { CountryViewModel(get()) }
}

val repoModule = module {
    single<DispatcherProvider> { DispatcherProviderImpl }
    single<CountryRepo> {
        CountryRepoImpl(get(), get())
    }
}

val serviceModule = module {
    single<CountryApiService> {
        val host = "https://gist.githubusercontent.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(CountryApiService::class.java)
    }
}