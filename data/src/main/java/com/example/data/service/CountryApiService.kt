package com.example.data.service

import com.example.data.models.CountryResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Nitin Dasari on 10/5/22.
 */
interface CountryApiService {
    @GET("peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
    suspend fun getCountries(): Response<List<CountryResponse>>
}