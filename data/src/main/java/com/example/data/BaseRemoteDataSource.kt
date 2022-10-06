package com.example.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by Nitin Dasari on 10/5/22.
 */

abstract class BaseRemoteDataSource {

    /**
     * @param dispatcher will be necessary if we want to test our repository.
     * @param apiCall - suspend function that calls the service to fetch data from network
     */
    protected suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> Response<T>,
    ): ResultWrapper<T> {
        return try {
            withContext(dispatcher) {
                val response = apiCall()
                if (response.isSuccessful && response.body() != null) {
                    ResultWrapper.Success(response.body())
                } else {
                    ResultWrapper.GenericError("Error fetching data")
                }
            }
        } catch (exception: Exception) {
            ResultWrapper.NetworkError(exception.localizedMessage)
        }
    }
}
