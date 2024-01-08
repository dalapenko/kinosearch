package tech.dalapenko.data.search.mapper

import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.core.network.dto.CountryDto
import tech.dalapenko.core.network.dto.FilmSearchItemDto
import tech.dalapenko.core.network.dto.GenreDto
import tech.dalapenko.core.network.mapper.DtoMapper
import javax.inject.Inject

internal class SearchResultDtoMapper @Inject constructor() :
    DtoMapper<FilmSearchItemDto, SearchResult> {

    override fun mapToModel(dto: FilmSearchItemDto): SearchResult {
        return SearchResult(
            id = dto.id ?: -1,
            ruName = dto.nameRu,
            originName = dto.nameEn,
            type = dto.type,
            description = dto.description,
            duration = dto.filmLength,
            year = dto.year,
            posterUrl = dto.posterUrl,
            posterUrlPreview = dto.posterUrlPreview,
            countryList = dto.countries?.mapNotNull(CountryDto::country) ?: emptyList(),
            genreList = dto.genres?.mapNotNull(GenreDto::genre) ?: emptyList(),
            rating = dto.rating,
            ratingVoteCount = dto.ratingVoteCount ?: 0
        )
    }

    override fun mapToDto(model: SearchResult): FilmSearchItemDto {
        throw NotImplementedError()
    }
}