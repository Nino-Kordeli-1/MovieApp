package com.data.dto.genre

import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponseDto(
    val genres: List<GenreResponseDto>
)

@Serializable
data class GenreResponseDto(
    val id: Int,
    val name: String
)