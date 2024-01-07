package tech.dalapenko.data.releases.mapper

import tech.dalapenko.data.core.mapper.DtoMapper
import tech.dalapenko.data.releases.model.Release
import tech.dalapenko.network.dto.CountryDto
import tech.dalapenko.network.dto.DigitalReleaseItemDto
import tech.dalapenko.network.dto.GenreDto
import javax.inject.Inject

internal class ReleaseDtoMapper @Inject constructor() : DtoMapper<DigitalReleaseItemDto, Release> {

    override fun mapToModel(dto: DigitalReleaseItemDto): Release {
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

    override fun mapToDto(model: Release): DigitalReleaseItemDto {
        throw NotImplementedError()
    }
}