package tech.dalapenko.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PremiereItemDto(
    @SerialName("kinopoiskId") val id: Int?,
    @SerialName("nameRu") val nameRu: String?,
    @SerialName("nameEn") val nameEn: String?,
    @SerialName("year") val year: Int?,
    @SerialName("posterUrl") val posterUrl: String?,
    @SerialName("posterUrlPreview") val posterUrlPreview: String?,
    @SerialName("countries") val countries: List<CountryDto>?,
    @SerialName("genres") val genres: List<GenreDto>?,
    @SerialName("duration") val duration: Int?,
    @SerialName("premiereRu") val premiereRu: String?,
)