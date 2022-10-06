package com.example.data.repo

import com.example.data.TestDispatcherProviderImpl
import com.example.data.models.CountryResponse
import com.example.data.service.CountryApiService
import com.example.domain.repo.CountryServiceState
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

/**
 * Created by Nitin Dasari on 10/6/22.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CountryRepoTest {

    private class Injector {
        val apiService = mock<CountryApiService>()
        val testDispatcher = TestDispatcherProviderImpl
        fun inject() = CountryRepoImpl(
            apiService, testDispatcher
        )

        val json = "[\n" +
                "  {\n" +
                "    \"capital\": \"Kabul\",\n" +
                "    \"code\": \"AF\",\n" +
                "    \"currency\": {\n" +
                "      \"code\": \"AFN\",\n" +
                "      \"name\": \"Afghan afghani\",\n" +
                "      \"symbol\": \"؋\"\n" +
                "    },\n" +
                "    \"flag\": \"https://restcountries.eu/data/afg.svg\",\n" +
                "    \"language\": {\n" +
                "      \"code\": \"ps\",\n" +
                "      \"name\": \"Pashto\"\n" +
                "    },\n" +
                "    \"name\": \"Afghanistan\",\n" +
                "    \"region\": \"AS\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"capital\": \"Mariehamn\",\n" +
                "    \"code\": \"AX\",\n" +
                "    \"currency\": {\n" +
                "      \"code\": \"EUR\",\n" +
                "      \"name\": \"Euro\",\n" +
                "      \"symbol\": \"€\"\n" +
                "    },\n" +
                "    \"flag\": \"https://restcountries.eu/data/ala.svg\",\n" +
                "    \"language\": {\n" +
                "      \"code\": \"sv\",\n" +
                "      \"name\": \"Swedish\"\n" +
                "    },\n" +
                "    \"name\": \"Åland Islands\",\n" +
                "    \"region\": \"EU\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"capital\": \"Tirana\",\n" +
                "    \"code\": \"AL\",\n" +
                "    \"currency\": {\n" +
                "      \"code\": \"ALL\",\n" +
                "      \"name\": \"Albanian lek\",\n" +
                "      \"symbol\": \"L\"\n" +
                "    },\n" +
                "    \"flag\": \"https://restcountries.eu/data/alb.svg\",\n" +
                "    \"language\": {\n" +
                "      \"code\": \"sq\",\n" +
                "      \"name\": \"Albanian\"\n" +
                "    },\n" +
                "    \"name\": \"Albania\",\n" +
                "    \"region\": \"EU\"\n" +
                "  }]"

        fun mockResponse(): List<CountryResponse> =
            try {
                val listType = object : TypeToken<List<CountryResponse?>?>() {}.type
                Gson().fromJson(json, listType)
            } catch (exception: JsonSyntaxException) {
                emptyList()
            }
    }

    @Test
    fun `when api service returns success then check if the correct domain model is created`() =
        runTest {
            val injector = Injector()

            whenever(injector.apiService.getCountries()).thenReturn(Response.success(injector.mockResponse()))
            val repo = injector.inject()
            val actualResponse = repo.getCountries()
            Assert.assertTrue(actualResponse is CountryServiceState.ServiceSuccess)
            val success = actualResponse as CountryServiceState.ServiceSuccess
            Assert.assertEquals(3, success.countriesList.size)
            Assert.assertEquals("Afghanistan", success.countriesList.first().name)
            Assert.assertEquals("Albania", success.countriesList.last().name)
        }

    @Test
    fun `when api service returns failure then check if the correct error message is created`() =
        runTest {
            val injector = Injector()

            whenever(injector.apiService.getCountries()).thenThrow(RuntimeException("Test Error"))
            val repo = injector.inject()
            val actualResponse = repo.getCountries()
            Assert.assertTrue(actualResponse is CountryServiceState.ServiceFailure)
            val failure = actualResponse as CountryServiceState.ServiceFailure
            Assert.assertEquals("Test Error", failure.errorMessage)
        }

    @Test
    fun `when api service returns http error then check if the correct error message is created`() =
        runTest {
            val injector = Injector()

            whenever(injector.apiService.getCountries()).thenReturn(
                Response.error(
                    400,
                    ResponseBody.create(null, "")
                )
            )
            val repo = injector.inject()
            val actualResponse = repo.getCountries()
            Assert.assertTrue(actualResponse is CountryServiceState.ServiceFailure)
            val failure = actualResponse as CountryServiceState.ServiceFailure
            Assert.assertEquals("Error fetching data", failure.errorMessage)
        }
}