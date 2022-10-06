package com.example.data

/**
 * Created by Nitin Dasari on 10/5/22.
 */

sealed class ResultWrapper<out T> {
    data class Success<T>(val payload: T?) : ResultWrapper<T>()
    data class GenericError(val message: String) : ResultWrapper<Nothing>()
    data class NetworkError(val message: String) : ResultWrapper<Nothing>()
}
