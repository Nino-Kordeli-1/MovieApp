package com.common.resource

sealed class NetworkResult<out D : Any> {
    data class Success<out D : Any>(val data: D) : NetworkResult<D>()
    data object Loading : NetworkResult<Nothing>()
    data class Error(
        val errorMessage: String,
        val throwable: Throwable? = null
    ) : NetworkResult<Nothing>()
}