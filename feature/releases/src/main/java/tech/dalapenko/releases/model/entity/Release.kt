package tech.dalapenko.releases.model.entity

import tech.dalapenko.network.dto.CountryDto
import tech.dalapenko.network.dto.DigitalReleaseItemDto
import tech.dalapenko.network.dto.GenreDto

data class Release(
    val id: Int,
    val ruName: String?,
    val originName: String?,
    val year: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val countryList: List<String>,
    val genreList: List<String>,
    val rating: Float,
    val ratingVoteCount: Int,
    val expectationsRating: Float,
    val expectationsRatingVoteCount: Int,
    val duration: Int?,
    val releaseDate: String?
) {

    companion object {
        fun mapFromDto(dto: DigitalReleaseItemDto): Release {
            return Release(
                id = dto.id ?: -1,
                ruName = dto.nameRu,
                originName = dto.nameEn,
                year = dto.year,
                posterUrl = dto.posterUrl,
                posterUrlPreview = dto.posterUrlPreview,
                countryList = dto.countries?.mapNotNull(CountryDto::country) ?: emptyList(),
                genreList = dto.genres?.mapNotNull(GenreDto::genre) ?: emptyList(),
                rating = dto.rating ?: 0f,
                ratingVoteCount = dto.ratingVoteCount ?: 0,
                expectationsRating = dto.expectationsRating ?: 0f,
                expectationsRatingVoteCount = dto.expectationsRatingVoteCount ?: 0,
                duration = dto.duration,
                releaseDate = dto.releaseDate,
            )
        }
    }
}