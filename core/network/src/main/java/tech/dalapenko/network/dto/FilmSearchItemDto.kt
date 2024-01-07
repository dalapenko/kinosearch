package tech.dalapenko.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmSearchItemDto(
    @SerialName("filmId") val id: Int? = null,
    @SerialName("nameRu") val nameRu: String? = null,
    @SerialName("nameEn") val nameEn: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("year") val year: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("filmLength") val filmLength: String? = null,
    @SerialName("countries") val countries: List<CountryDto>? = null,
    @SerialName("genres") val genres: List<GenreDto>? = null,
    @SerialName("rating") val rating: String? = null,
    @SerialName("ratingVoteCount") val ratingVoteCount: Int? = null,
    @SerialName("posterUrl") val posterUrl: String? = null,
    @SerialName("posterUrlPreview") val posterUrlPreview: String? = null
)