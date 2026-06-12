package com.example.common.mapper

import com.example.common.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun <Dto : Any, Domain : Any> Flow<Resource<Dto>>.asResource(
    onSuccess: suspend (Dto) -> Domain,
): Flow<Resource<Domain>> {
    return this.map {
        when (it) {
            is Resource.Error<*> -> TODO()
            is Resource.Loading<*> -> TODO()
            is Resource.Success<*> -> TODO()
        }
    }
}