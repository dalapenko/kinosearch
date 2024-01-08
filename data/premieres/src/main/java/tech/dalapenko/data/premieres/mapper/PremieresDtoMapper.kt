package tech.dalapenko.data.premieres.mapper

import tech.dalapenko.data.core.mapper.DtoMapper
import tech.dalapenko.data.premieres.model.Premiere
import tech.dalapenko.network.dto.CountryDto
import tech.dalapenko.network.dto.GenreDto
import tech.dalapenko.network.dto.PremiereItemDto
import javax.inject.Inject

internal class PremieresDtoMapper @Inject constructor() : DtoMapper<PremiereItemDto, Premiere> {

    override fun mapToModel(dto: PremiereItemDto): Premiere {
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

    override fun mapToDto(model: Premiere): PremiereItemDto {
        throw NotImplementedError()
    }
}