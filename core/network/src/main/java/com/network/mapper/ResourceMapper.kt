package com.network.mapper

import com.common.resource.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <Dto : Any, Domain : Any> Flow<NetworkResult<Dto>>.asResource(
    mapper: suspend (Dto) -> Domain
): Flow<NetworkResult<Domain>> {
    return this.map { result ->
        when (result) {

            is NetworkResult.Loading ->
                NetworkResult.Loading

            is NetworkResult.Error ->
                NetworkResult.Error(
                    errorMessage = result.errorMessage,
                    throwable = result.throwable
                )

            is NetworkResult.Success -> try {
                NetworkResult.Success(
                    mapper(result.data)
                )
            } catch (e: Exception) {
                NetworkResult.Error(
                    errorMessage = e.message ?: "Mapping failed",
                    throwable = e
                )
            }
        }
    }
}