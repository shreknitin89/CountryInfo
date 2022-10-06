package com.example.data.repo

import com.example.data.BaseRemoteDataSource
import com.example.data.DispatcherProvider
import com.example.data.ResultWrapper
import com.example.data.service.CountryApiService
import com.example.domain.models.Country
import com.example.domain.repo.CountryRepo
import com.example.domain.repo.CountryServiceState


/**
 * Created by Nitin Dasari on 10/5/22.
 */
class CountryRepoImpl constructor(
    private val apiService: CountryApiService,
    private val dispatchers: DispatcherProvider,
) : CountryRepo, BaseRemoteDataSource() {
    override suspend fun getCountries(): CountryServiceState {
        val remoteResultWrapper = safeApiCall(dispatchers.io) {
            apiService.getCountries()
        }
        return when (remoteResultWrapper) {
            is ResultWrapper.Success -> {
                val countries = remoteResultWrapper.payload?.map {
                    Country(
                        it.name,
                        it.code,
                        it.region,
                        it.capital,
                        it.flag,
                        it.language.name,
                        it.language.code ?: "n/a",
                        it.currency.name,
                        it.currency.code,
                        it.currency.symbol ?: "n/a"
                    )
                } ?: emptyList()
                CountryServiceState.ServiceSuccess(countries)
            }
            is ResultWrapper.GenericError -> {
                val error = remoteResultWrapper.message
                CountryServiceState.ServiceFailure(error)
            }
            is ResultWrapper.NetworkError -> {
                val error = remoteResultWrapper.message
                CountryServiceState.ServiceFailure(error)
            }
        }
    }
}