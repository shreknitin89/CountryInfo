package com.example.countryinfo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Country
import com.example.domain.repo.CountryRepo
import com.example.domain.repo.CountryServiceState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * Created by Nitin Dasari on 10/5/22.
 */
class CountryViewModel constructor(
    private val countryRepo: CountryRepo,
) : ViewModel() {
    private val countriesList = ArrayList<Country>()

    private val _uiStateFlow =
        MutableSharedFlow<UiState>(replay = 1)
    val uiStateFlow: Flow<UiState> get() = _uiStateFlow
    private val _countrySelectionFlow =
        MutableSharedFlow<Country>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val countrySelectionFlow: Flow<Country> get() = _countrySelectionFlow

    private val _countryDetailsFlow = MutableSharedFlow<CountryUiState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val countryDetailsFlow: Flow<CountryUiState> get() = _countryDetailsFlow

    private val countryClickListener: (Country) -> Unit = {
        _countrySelectionFlow.tryEmit(it)
    }

    init {
        fetchCountriesList()
    }

    fun fetchCountriesList() = viewModelScope.launch {
        _uiStateFlow.tryEmit(UiState.Loading)
        when (val serviceState = countryRepo.getCountries()) {
            is CountryServiceState.ServiceSuccess -> {
                val countries = serviceState.countriesList
                countriesList.clear()
                countriesList.addAll(countries)
                _uiStateFlow.tryEmit(
                    UiState.Success(
                        countries.map {
                            CountryItemHelper(it, countryClickListener)
                        }
                    )
                )
            }
            is CountryServiceState.ServiceFailure -> _uiStateFlow.tryEmit(
                UiState.Error(serviceState.errorMessage)
            )
        }
    }

    fun checkCountryInfo(countryCode: String?) {
        _countryDetailsFlow.tryEmit(CountryUiState.Loading)
        val country = countriesList.firstOrNull { it.code == countryCode }
        if (country != null) {
            _countryDetailsFlow.tryEmit(CountryUiState.Success(country))
        } else {
            _countryDetailsFlow.tryEmit(CountryUiState.Error("No Country Found"))
        }
    }
}

sealed interface UiState {
    object Loading : UiState
    data class Success(val countries: List<CountryItemHelper>) : UiState
    data class Error(val errorMessage: String) : UiState
}

sealed interface CountryUiState {
    object Loading : CountryUiState
    data class Success(val country: Country) : CountryUiState
    data class Error(val errorMessage: String) : CountryUiState
}