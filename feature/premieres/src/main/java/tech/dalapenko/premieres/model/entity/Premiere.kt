package tech.dalapenko.premieres.model.entity

import tech.dalapenko.network.dto.CountryDto
import tech.dalapenko.network.dto.GenreDto
import tech.dalapenko.network.dto.PremiereItemDto

data class Premiere(
    val id: Int,
    val ruName: String?,
    val originName: String?,
    val year: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val countryList: List<String>,
    val genreList: List<String>,
    val duration: Int?,
    val premiereDate: String?
) {

    companion object {
        fun mapFromDto(dto: PremiereItemDto): Premiere {
            return Premiere(
                id = dto.id ?: -1,
                ruName = dto.nameRu,
                originName = dto.nameEn,
                year = dto.year,
                posterUrl = dto.posterUrl,
                posterUrlPreview = dto.posterUrlPreview,
                countryList = dto.countries?.mapNotNull(CountryDto::country) ?: emptyList(),
                genreList = dto.genres?.mapNotNull(GenreDto::genre) ?: emptyList(),
                duration = dto.duration,
                premiereDate = dto.premiereRu,
            )
        }
    }
}