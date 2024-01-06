package tech.dalapenko.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DigitalReleaseItemDto(
    @SerialName("filmId") val id: Int? = null,
    @SerialName("nameRu") val nameRu: String? = null,
    @SerialName("nameEn") val nameEn: String? = null,
    @SerialName("year") val year: Int? = null,
    @SerialName("posterUrl") val posterUrl: String? = null,
    @SerialName("posterUrlPreview") val posterUrlPreview: String? = null,
    @SerialName("countries") val countries: List<CountryDto>? = null,
    @SerialName("genres") val genres: List<GenreDto>? = null,
    @SerialName("rating") val rating: Float? = null,
    @SerialName("ratingVoteCount") val ratingVoteCount: Int? = null,
    @SerialName("expectationsRating") val expectationsRating: Float? = null,
    @SerialName("expectationsRatingVoteCount") val expectationsRatingVoteCount: Int? = null,
    @SerialName("duration") val duration: Int? = null,
    @SerialName("releaseDate") val releaseDate: String? = null
)