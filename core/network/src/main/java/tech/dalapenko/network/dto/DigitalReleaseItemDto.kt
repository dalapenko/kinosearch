package tech.dalapenko.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DigitalReleaseItemDto(
    @SerialName("filmId") val id: Int?,
    @SerialName("nameRu") val nameRu: String?,
    @SerialName("nameEn") val nameEn: String?,
    @SerialName("year") val year: Int?,
    @SerialName("posterUrl") val posterUrl: String?,
    @SerialName("posterUrlPreview") val posterUrlPreview: String?,
    @SerialName("countries") val countries: List<CountryDto>?,
    @SerialName("genres") val genres: List<GenreDto>?,
    @SerialName("rating") val rating: Float?,
    @SerialName("ratingVoteCount") val ratingVoteCount: Int?,
    @SerialName("expectationsRating") val expectationsRating: Float?,
    @SerialName("expectationsRatingVoteCount") val expectationsRatingVoteCount: Int?,
    @SerialName("duration") val duration: Int?,
    @SerialName("releaseDate") val releaseDate: String?,
)