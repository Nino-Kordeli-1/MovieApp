package com.data.mapper

import com.common.mapper.BaseMapper
import com.data.dto.genre.GenreResponseDto
import com.domain.model.GenreResponse

class GenreMapper : BaseMapper<GenreResponseDto, GenreResponse> {
    override fun map(from: GenreResponseDto): GenreResponse {
        return GenreResponse(
            id = from.id,
            name = from.name
        )
    }
}