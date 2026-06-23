package com.data.mapper

import com.data.dto.genre.GenreResponseDto
import com.domain.model.GenreResponse

fun GenreResponseDto.toDomain(): GenreResponse {
    return GenreResponse(
        id = id,
        name = name
    )
}