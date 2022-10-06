package com.example.domain.repo

import com.example.domain.models.Country

/**
 * Created by Nitin Dasari on 10/5/22.
 */
interface CountryRepo {
    suspend fun getCountries(): CountryServiceState
}

sealed interface CountryServiceState {
    data class ServiceSuccess(val countriesList: List<Country>) : CountryServiceState
    data class ServiceFailure(val errorMessage: String) : CountryServiceState
}