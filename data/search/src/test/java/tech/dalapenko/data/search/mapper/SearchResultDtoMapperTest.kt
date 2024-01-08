package tech.dalapenko.data.search.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import tech.dalapenko.core.network.dto.FilmSearchItemDto
import tech.dalapenko.data.search.model.SearchResult

class SearchResultDtoMapperTest {

    private val mapper = SearchResultDtoMapper()

    @Test
    fun mapToModelWithEmptyData() {
        val actualMappedModel = mapper.mapToModel(FilmSearchItemDto())
        val expectedMappedModel = SearchResult(
            id = -1, null, null, null, null, null, null,
            emptyList(), emptyList(), null, null, null, null)

        assertEquals(actualMappedModel, expectedMappedModel)
    }

    @Test
    fun mapToModelWithFullData() {
        val actualMappedModel = mapper.mapToModel(
            FilmSearchItemDto(
                id = 1, nameRu = "тест", nameEn = "test", type = "FILM", year = "2022",
                description = "Film for test", filmLength = "136", countries = emptyList(),
                genres = emptyList(), rating = "99%", ratingVoteCount = 78,
                posterUrl = "https://test.com/img.jpg", posterUrlPreview = "https://test.com/img-preview.jpg"
            )
        )

        val expectedMappedModel = SearchResult(
            id = 1, ruName = "тест", originName = "test", type = "FILM", year = "2022",
            description = "Film for test", duration = "136", countryList = emptyList(),
            genreList = emptyList(), rating = "99%", ratingVoteCount = 78,
            posterUrl = "https://test.com/img.jpg", posterUrlPreview = "https://test.com/img-preview.jpg"
        )

        assertEquals(actualMappedModel, expectedMappedModel)
    }

    @Test
    fun mapToDtoNotImplementedTest() {
        val mockedFilm = SearchResult(
            id = 1, null, null, null, null, null, null,
            emptyList(), emptyList(), null, null, null, null)

        assertThrows(NotImplementedError::class.java) {
            mapper.mapToDto(mockedFilm)
        }
    }
}