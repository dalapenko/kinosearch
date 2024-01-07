package tech.dalapenko.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDto(
    @SerialName("kinopoiskId") val id: Int? = null,
    @SerialName("kinopoiskHDId") val hdId: String? = null,
    @SerialName("imdbId") val imdbId: String? = null,
    @SerialName("nameRu") val nameRu: String? = null,
    @SerialName("nameEn") val nameEn: String? = null,
    @SerialName("nameOriginal") val nameOriginal: String? = null,
    @SerialName("posterUrl") val posterUrl: String? = null,
    @SerialName("posterUrlPreview") val posterUrlPreview: String? = null,
    @SerialName("coverUrl") val coverUrl: String? = null,
    @SerialName("logoUrl") val logoUrl: String? = null,
    @SerialName("reviewsCount") val reviewsCount: Int? = null,
    @SerialName("ratingGoodReview") val ratingGoodReview: Float? = null,
    @SerialName("ratingGoodReviewVoteCount") val ratingGoodReviewVoteCount: Int? = null,
    @SerialName("ratingKinopoisk") val ratingKinopoisk: Float? = null,
    @SerialName("ratingKinopoiskVoteCount") val ratingKinopoiskVoteCount: Int? = null,
    @SerialName("ratingImdb") val ratingImdb: Float? = null,
    @SerialName("ratingImdbVoteCount") val ratingImdbVoteCount: Int? = null,
    @SerialName("ratingFilmCritics") val ratingFilmCritics: Float? = null,
    @SerialName("ratingFilmCriticsVoteCount") val ratingFilmCriticsVoteCount: Int? = null,
    @SerialName("ratingAwait") val ratingAwait: Float? = null,
    @SerialName("ratingAwaitCount") val ratingAwaitCount: Int? = null,
    @SerialName("ratingRfCritics") val ratingRfCritics: Float? = null,
    @SerialName("ratingRfCriticsVoteCount") val ratingRfCriticsVoteCount: Int? = null,
    @SerialName("webUrl") val webUrl: String? = null,
    @SerialName("year") val year: Int? = null,
    @SerialName("filmLength") val filmLength: Int? = null,
    @SerialName("slogan") val slogan: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("shortDescription") val shortDescription: String? = null,
    @SerialName("editorAnnotation") val editorAnnotation: String? = null,
    @SerialName("isTicketsAvailable") val isTicketsAvailable: Boolean? = null,
    @SerialName("productionStatus") val productionStatus: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("ratingMpaa") val ratingMpaa: String? = null,
    @SerialName("ratingAgeLimits") val ratingAgeLimits: String? = null,
    @SerialName("hasImax") val hasImax: Boolean? = null,
    @SerialName("has3D") val has3D: Boolean? = null,
    @SerialName("lastSync") val lastSync: String? = null,
    @SerialName("countries") val countries: List<CountryDto>? = null,
    @SerialName("genres") val genres: List<GenreDto>? = null,
    @SerialName("startYear") val startYear: Int? = null,
    @SerialName("endYear") val endYear: Int? = null,
    @SerialName("serial") val serial: Boolean? = null,
    @SerialName("shortFilm") val shortFilm: Boolean? = null,
    @SerialName("completed") val completed: Boolean? = null
)