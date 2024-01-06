package tech.dalapenko.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmSearchDto(
    @SerialName("keyword") val keyword: String?,
    @SerialName("pagesCount") val pagesCount: Int?,
    @SerialName("searchFilmsCountResult") val searchFilmsCountResult: Int?,
    @SerialName("films") val films: List<FilmSearchItemDto>?
)