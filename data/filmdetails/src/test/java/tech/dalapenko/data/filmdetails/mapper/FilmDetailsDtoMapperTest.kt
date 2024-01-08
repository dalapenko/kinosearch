package tech.dalapenko.data.filmdetails.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import tech.dalapenko.core.network.dto.FilmDto
import tech.dalapenko.data.filmdetails.model.Film

class FilmDetailsDtoMapperTest {

    private val mapper = FilmDetailsDtoMapper()

    @Test
    fun mapToModelWithEmptyData() {
        val actualMappedModel = mapper.mapToModel(FilmDto())
        val expectedMappedModel = Film(
            id = -1, null, null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null,
            null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, emptyList(), emptyList(), null,
            null, null, null, null)

        assertEquals(actualMappedModel, expectedMappedModel)
    }

    @Test
    fun mapToModelWithFullData() {
        val actualMappedModel = mapper.mapToModel(FilmDto(
            id = 1, hdId = "1", imdbId =  "1", nameRu = "тест", nameEn = "test", nameOriginal = "test",
            posterUrl = "https://test.com/img.jpg", posterUrlPreview = "https://test.com/img-preview.jpg",
            coverUrl = "https://test.com/img-cover.jpg", logoUrl = "https://test.com/img-logo.jpg",
            reviewsCount = 1, ratingGoodReview = 8.7f,
            ratingGoodReviewVoteCount = 2, ratingKinopoisk = 8.7f, ratingKinopoiskVoteCount = 2,
            ratingImdb = 6.9f, ratingImdbVoteCount = 3, ratingFilmCritics = 7.1f,
            ratingFilmCriticsVoteCount =  5, ratingAwait = 8.1f,
            ratingAwaitCount = 4, ratingRfCritics = 6.9f, ratingRfCriticsVoteCount = 6,
            webUrl = "https://test.com/film/1", year = 2022, filmLength = 136, slogan = "Test film",
            description = "Film for test", shortDescription = "test", editorAnnotation = "for test",
            isTicketsAvailable = false, productionStatus = "POST_PRODUCTION",
            type = "FILM", ratingMpaa = "r", ratingAgeLimits = "age18", hasImax = true,
            has3D = false, lastSync = "2021-07-29T20:07:49.109817", countries = emptyList(),
            genres = emptyList(), startYear = 2018, endYear = 2021, serial = true,
            shortFilm = false, completed = true))

        val expectedMappedModel = Film(
            id = 1, hdId = "1", imdbId =  "1", ruName = "тест", enName = "test", originName = "test",
            posterUrl = "https://test.com/img.jpg", posterUrlPreview = "https://test.com/img-preview.jpg",
            coverUrl = "https://test.com/img-cover.jpg", logoUrl = "https://test.com/img-logo.jpg",
            reviewsCount = 1, ratingGoodReview = 8.7f,
            ratingGoodReviewVoteCount = 2, ratingKinopoisk = 8.7f, ratingKinopoiskVoteCount = 2,
            ratingImdb = 6.9f, ratingImdbVoteCount = 3, ratingFilmCritics = 7.1f,
            ratingFilmCriticsVoteCount =  5, ratingAwait = 8.1f,
            ratingAwaitCount = 4, ratingRfCritics = 6.9f, ratingRfCriticsVoteCount = 6,
            webUrl = "https://test.com/film/1", year = 2022, filmLength = 136, slogan = "Test film",
            description = "Film for test", shortDescription = "test", editorAnnotation = "for test",
            isTicketsAvailable = false, productionStatus = "POST_PRODUCTION",
            type = "FILM", ratingMpaa = "r", ratingAgeLimits = "age18", hasImax = true,
            has3D = false, lastSync = "2021-07-29T20:07:49.109817", countryList = emptyList(),
            genreList = emptyList(), startYear = 2018, endYear = 2021, serial = true,
            shortFilm = false, completed = true)

        assertEquals(actualMappedModel, expectedMappedModel)
    }

    @Test
    fun mapToDtoNotImplementedTest() {
        val mockedFilm = Film(
            id = 1, null, null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null,
            null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, emptyList(), emptyList(), null,
            null, null, null, null)

        assertThrows(NotImplementedError::class.java) {
            mapper.mapToDto(mockedFilm)
        }
    }
}