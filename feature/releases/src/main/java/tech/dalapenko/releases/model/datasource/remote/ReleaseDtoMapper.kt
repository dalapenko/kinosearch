package tech.dalapenko.releases.model.datasource.remote

import tech.dalapenko.network.dto.CountryDto
import tech.dalapenko.network.dto.DigitalReleaseItemDto
import tech.dalapenko.network.dto.GenreDto
import tech.dalapenko.releases.model.entity.Release
import javax.inject.Inject

class ReleaseDtoMapper @Inject constructor() {

    fun mapToRelease(dto: DigitalReleaseItemDto): Release {
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