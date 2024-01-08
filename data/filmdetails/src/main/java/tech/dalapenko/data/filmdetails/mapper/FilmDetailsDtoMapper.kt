package tech.dalapenko.data.filmdetails.mapper

import tech.dalapenko.data.filmdetails.model.Film
import tech.dalapenko.core.network.dto.CountryDto
import tech.dalapenko.core.network.dto.FilmDto
import tech.dalapenko.core.network.dto.GenreDto
import tech.dalapenko.core.network.mapper.DtoMapper
import javax.inject.Inject

internal class FilmDetailsDtoMapper @Inject constructor() :
    DtoMapper<FilmDto, Film> {

    override fun mapToModel(dto: FilmDto): Film {
        return Film(
            id = dto.id ?: -1,
            hdId = dto.hdId,
            imdbId = dto.imdbId,
            ruName = dto.nameRu,
            enName = dto.nameEn,
            originName = dto.nameOriginal,
            posterUrl = dto.posterUrl,
            posterUrlPreview = dto.posterUrlPreview,
            coverUrl = dto.coverUrl,
            logoUrl = dto.logoUrl,
            reviewsCount = dto.reviewsCount,
            ratingGoodReview = dto.ratingGoodReview,
            ratingGoodReviewVoteCount = dto.ratingGoodReviewVoteCount,
            ratingKinopoisk = dto.ratingKinopoisk,
            ratingKinopoiskVoteCount = dto.ratingKinopoiskVoteCount,
            ratingImdb = dto.ratingImdb,
            ratingImdbVoteCount = dto.ratingImdbVoteCount,
            ratingFilmCritics = dto.ratingFilmCritics,
            ratingFilmCriticsVoteCount = dto.ratingFilmCriticsVoteCount,
            ratingAwait = dto.ratingAwait,
            ratingAwaitCount = dto.ratingAwaitCount,
            ratingRfCritics = dto.ratingRfCritics,
            ratingRfCriticsVoteCount = dto.ratingRfCriticsVoteCount,
            webUrl = dto.webUrl,
            year = dto.year,
            filmLength = dto.filmLength,
            slogan = dto.slogan,
            description = dto.description,
            shortDescription = dto.description,
            editorAnnotation = dto.shortDescription,
            isTicketsAvailable = dto.isTicketsAvailable,
            productionStatus = dto.productionStatus,
            type = dto.type,
            ratingMpaa = dto.ratingMpaa,
            ratingAgeLimits = dto.ratingAgeLimits,
            hasImax = dto.hasImax,
            has3D = dto.has3D,
            lastSync = dto.lastSync,
            countryList = dto.countries?.mapNotNull(CountryDto::country) ?: emptyList(),
            genreList = dto.genres?.mapNotNull(GenreDto::genre) ?: emptyList(),
            startYear = dto.startYear,
            endYear = dto.endYear,
            serial = dto.serial,
            shortFilm = dto.shortFilm,
            completed = dto.completed
        )
    }

    override fun mapToDto(model: Film): FilmDto {
        throw NotImplementedError()
    }
}