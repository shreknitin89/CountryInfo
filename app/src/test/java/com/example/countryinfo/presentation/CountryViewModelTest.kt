package com.example.countryinfo.presentation

import app.cash.turbine.test
import com.example.countryinfo.CoroutinesTestRule
import com.example.domain.models.Country
import com.example.domain.repo.CountryRepo
import com.example.domain.repo.CountryServiceState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private class Injector {
        val repo = mock<CountryRepo>()
        fun inject() = CountryViewModel(repo)
        val mockData = listOf(
            Country("USA", "USA", "NA", "", "", "", "", "", "", ""),
            Country("Canada", "CA", "NA", "", "", "", "", "", "", ""),
            Country("India", "IN", "AS", "", "", "", "", "", "", ""),
            Country("Japan", "JP", "AS", "", "", "", "", "", "", ""),
            Country("Amman", "JO", "AS", "", "", "", "", "", "", ""),
        )
    }

    @Test
    fun `when repo responds with successful data`() = runTest {
        val injector = Injector()
        val repo = injector.repo
        val mockData = injector.mockData
        val emptyAction: (Country) -> Unit = {}
        whenever(repo.getCountries()).thenReturn(CountryServiceState.ServiceSuccess(mockData))
        val expected = mockData.map { CountryItemHelper(it, emptyAction) }
        injector.inject().uiStateFlow.test {
            val success = awaitItem()
            Assert.assertTrue(success is UiState.Success)
            Assert.assertEquals(
                5,
                (success as UiState.Success).countries.size
            )
            Assert.assertEquals(
                expected.first().country,
                success.countries.first().country
            )
            Assert.assertEquals(
                expected.last().country.code,
                success.countries.last().country.code
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when repo responds with failure`() = runTest {
        val injector = Injector()
        val repo = injector.repo
        whenever(repo.getCountries()).thenReturn(CountryServiceState.ServiceFailure("Test Error"))
        injector.inject().uiStateFlow.test {
            val success = awaitItem()
            Assert.assertTrue(success is UiState.Error)
            Assert.assertEquals(
                "Test Error",
                (success as UiState.Error).errorMessage
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}