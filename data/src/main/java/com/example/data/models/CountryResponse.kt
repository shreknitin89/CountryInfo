package com.example.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Nitin Dasari on 10/5/22.
 *
 */
data class CountryResponse(
    val name: String,
    val code: String,
    val region: String,
    val capital: String,
    val flag: String,
    val language: LanguageResponse,
    val currency: CurrencyResponse,
)

data class LanguageResponse(
    val name: String,
    val code: String?,
)

data class CurrencyResponse(
    val name: String,
    val code: String,
    val symbol: String?,
)