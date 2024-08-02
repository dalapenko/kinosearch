package tech.dalapenko.data.search.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.core.network.api.KinoApi
import tech.dalapenko.core.network.dto.FilmSearchDto
import tech.dalapenko.core.network.dto.FilmSearchItemDto
import tech.dalapenko.core.network.mapper.DtoMapper
import tech.dalapenko.data.search.datasource.remote.PagingDataSource
import tech.dalapenko.data.search.model.SearchResult

class PagingDataSourceTest {

    private val mockkKinoApi = mockk<KinoApi>()
    private val mockkMapper = mockk<DtoMapper<FilmSearchItemDto, SearchResult>>()
    private val mockkFilmSearchItemDto = mockk<FilmSearchItemDto>()

    private val testPagingDataSource =
        PagingDataSource(mockkKinoApi, mockkMapper, TEST_SEARCH_KEYWORD)
    private val testFilmSearchDto = FilmSearchDto(
        keyword = TEST_SEARCH_KEYWORD,
        pagesCount = 1,
        searchFilmsCountResult = 1,
        films = listOf(mockkFilmSearchItemDto)
    )

    @Test
    fun getSearchResultWhenSuccessResponse() = runTest {
        val networkData = SearchResult(
            id = 1, null, null, null, null, null, null,
            emptyList(), emptyList(), null, null, null, null
        )

        coEvery {
            mockkKinoApi.getSearchResult(TEST_SEARCH_KEYWORD, DEFAULT_PAGE)
        } returns NetworkResponse.Success(testFilmSearchDto)

        every { mockkMapper.mapToModel(mockkFilmSearchItemDto) } returns networkData

        val testPager = TestPager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                initialLoadSize = DEFAULT_INITIAL_LOAD_SIZE
            ),
            pagingSource = testPagingDataSource
        )

        val result = testPager.refresh() as PagingSource.LoadResult.Page

        assertEquals(listOf(networkData), result.data)
        assertEquals(null, result.nextKey)
        assertEquals(null, result.prevKey)
    }

    @Test
    fun getSearchResultWhenErrorResponse() = runTest {
        val httpNetworkResponse = HttpNetworkException()

        coEvery {
            mockkKinoApi.getSearchResult(TEST_SEARCH_KEYWORD, DEFAULT_PAGE)
        } returns NetworkResponse.Error(
            code = TEST_BAD_RESPONSE_CODE,
            message = TEST_BAD_RESPONSE_MESSAGE,
            throwable = httpNetworkResponse
        )

        val testPager = TestPager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                initialLoadSize = DEFAULT_INITIAL_LOAD_SIZE
            ),
            pagingSource = testPagingDataSource
        )

        val result = testPager.refresh() as PagingSource.LoadResult.Error

        assertEquals(httpNetworkResponse, result.throwable)
    }

    @Test
    fun getSearchResultWhenExceptionResponse() = runTest {
        val nullPointerException = NullPointerException()

        coEvery {
            mockkKinoApi.getSearchResult(TEST_SEARCH_KEYWORD, DEFAULT_PAGE)
        } returns NetworkResponse.Exception(nullPointerException)

        val testPager = TestPager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGING_SIZE,
                initialLoadSize = DEFAULT_INITIAL_LOAD_SIZE
            ),
            pagingSource = testPagingDataSource
        )

        val result = testPager.refresh() as PagingSource.LoadResult.Error

        assertEquals(nullPointerException, result.throwable)
    }
}

private typealias HttpNetworkException = RuntimeException

private const val TEST_SEARCH_KEYWORD = "Ла-Ла Лэнд"
private const val TEST_BAD_RESPONSE_CODE = 400
private const val TEST_BAD_RESPONSE_MESSAGE = "Not found"

private const val DEFAULT_PAGE = 1
private const val DEFAULT_PAGING_SIZE = 20
private const val DEFAULT_INITIAL_LOAD_SIZE = 1