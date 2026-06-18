package com.common.mapper

import com.common.resource.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun <Dto : Any, Domain : Any> Flow<NetworkResult<Dto>>.asResource(
    onSuccess: suspend (Dto) -> Domain,
): Flow<NetworkResult<Domain>> {
    return this.map { result ->
        when (result) {
            is NetworkResult.Error -> NetworkResult.Error(
                errorMessage = result.errorMessage,
                throwable = result.throwable
            )

            NetworkResult.Loading -> NetworkResult.Loading
            is NetworkResult.Success -> try {
                NetworkResult.Success(onSuccess(result.data))
            } catch (e: Exception) {
                NetworkResult.Error(
                    errorMessage = e.message ?: "Mapping failed",
                    throwable = e
                )
            }
        }
    }
}