package com.network.response_handler

import com.common.resource.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.time.Duration.Companion.milliseconds

class ResponseHandler {
    fun <T : Any> apiCall(
        call: suspend () -> Response<T>
    ): Flow<NetworkResult<T>> = flow {
        emit(NetworkResult.Loading)
        try {
            val response = withTimeout(15_000.milliseconds) {
                call()
            }
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(NetworkResult.Success(body))
                } else {
                    emit(NetworkResult.Error("Empty response body"))
                }
            } else {
                emit(
                    NetworkResult.Error(
                        "HTTP ${response.code()}: ${response.message()}"
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                NetworkResult.Error(
                    errorMessage = handleException(e),
                    throwable = e
                )
            )
        }
    }

    private fun handleException(e: Throwable): String = when (e) {
        is HttpException -> "HTTP error: ${e.code()} ${e.response()?.message()}"
        is SocketTimeoutException -> "Connection timeout. Please check your network connection."
        is UnknownHostException -> "No internet connection. Please check your network settings."
        is IOException -> "Network error: IO Exception occurred."
        else -> "Unknown error occurred: ${e.message}"
    }
}