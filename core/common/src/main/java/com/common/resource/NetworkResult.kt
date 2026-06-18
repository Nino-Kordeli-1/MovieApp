package com.common.resource

sealed class NetworkResult<out D : Any> {
    data class Success<out D : Any>(val data: D) : NetworkResult<D>()
    data class Loading<Nothing : Any>(val loading: Boolean) : NetworkResult<Nothing>()
    data class Error<out D : Any>(val errorMessage: String) : NetworkResult<D>()
}