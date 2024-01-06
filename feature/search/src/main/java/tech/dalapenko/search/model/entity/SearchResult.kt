package tech.dalapenko.search.model.entity

import tech.dalapenko.network.dto.CountryDto
import tech.dalapenko.network.dto.FilmSearchItemDto
import tech.dalapenko.network.dto.GenreDto

data class SearchResult(
    val id: Int,
    val ruName: String?,
    val originName: String?,
    val type: String?,
    val year: Int?,
    val description: String?,
    val duration: String?,
    val countryList: List<String>,
    val genreList: List<String>,
    val rating: String?,
    val ratingVoteCount: Int,
    val posterUrl: String?,
    val posterUrlPreview: String?
) {
    companion object {
        fun mapFromDto(dto: FilmSearchItemDto): SearchResult {
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
    }
}