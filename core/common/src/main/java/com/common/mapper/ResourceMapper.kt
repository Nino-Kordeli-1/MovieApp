package com.common.mapper

import com.common.resource.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun <Dto : Any, Domain : Any> Flow<NetworkResult<Dto>>.asResource(
    onSuccess: suspend (Dto) -> Domain,
): Flow<NetworkResult<Domain>> {
    return this.map {
        when (it) {
            is NetworkResult.Error<*> -> TODO()
            is NetworkResult.Loading<*> -> TODO()
            is NetworkResult.Success<*> -> TODO()
        }
    }
}