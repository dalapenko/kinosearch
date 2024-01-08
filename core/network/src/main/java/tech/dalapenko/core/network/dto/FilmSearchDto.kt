package tech.dalapenko.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmSearchDto(
    @SerialName("keyword") val keyword: String? = null,
    @SerialName("pagesCount") val pagesCount: Int? = null,
    @SerialName("searchFilmsCountResult") val searchFilmsCountResult: Int? = null,
    @SerialName("films") val films: List<FilmSearchItemDto>? = null
)