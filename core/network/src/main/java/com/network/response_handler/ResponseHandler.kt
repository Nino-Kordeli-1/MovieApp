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

fun <T : Any> apiCall(
    call: suspend () -> Response<T>
): Flow<NetworkResult<T>> = flow {
    emit(NetworkResult.Loading)
    try {
        val response = withTimeout(15_000.milliseconds) { call() }
        emit(response.toNetworkResult())
    } catch (e: Exception) {
        emit(NetworkResult.Error(errorMessage = handleException(e), throwable = e))
    }
}

private fun <T : Any> Response<T>.toNetworkResult(): NetworkResult<T> {
    if (isSuccessful) {
        val body = body()
        return if (body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error("Empty response body")
        }
    }

    return when (code()) {
        401 -> NetworkResult.Error("Unauthorized 401")
        403 -> NetworkResult.Error("Forbidden - to access to this resource")
        404 -> NetworkResult.Error("Not Found")
        in 400..499 -> NetworkResult.Error("Client error: ${code()}")
        in 500..599 -> NetworkResult.Error("Server error ${code()}")
        else -> NetworkResult.Error("Unexpected HTTP ${code()} : ${message()}")
    }
}

private fun handleException(e: Throwable): String = when (e) {
    is HttpException -> "HTTP error: ${e.code()} ${e.response()?.message()}"
    is SocketTimeoutException -> "Connection timeout. Please check your network connection."
    is UnknownHostException -> "No internet connection. Please check your network settings."
    is IOException -> "Network error: IO Exception occurred."
    else -> "Unknown error occurred: ${e.message}"
}