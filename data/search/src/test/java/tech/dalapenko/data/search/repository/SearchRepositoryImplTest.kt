package tech.dalapenko.data.search.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.data.search.datasource.remote.RemoteDataSource
import tech.dalapenko.data.search.model.SearchResult

class SearchRepositoryImplTest {

    private val mockkRemoteRepository = mockk<RemoteDataSource>()
    @Test
    fun getSearchResultWhenSuccessResponse() = runTest {
        val networkResponse = SearchResult(
            id = 1, null, null, null, null, null, null,
            emptyList(), emptyList(), null, null, null, null
        )

        coEvery {
            mockkRemoteRepository.getSearchResult(DEFAULT_SEARCH_KEYWORD)
        } returns NetworkResponse.Success(listOf(networkResponse))
        val repository = SearchRepositoryImpl(mockkRemoteRepository)

        val dataFlow = repository.getSearchResult(DEFAULT_SEARCH_KEYWORD)
        val lastEmittedData = dataFlow.last()

        assertEquals(lastEmittedData::class.java, DataState.Ready::class.java)

        assertArrayEquals((lastEmittedData as? DataState.Ready)?.data?.toTypedArray(), arrayOf(networkResponse))
    }

    @Test
    fun getSearchResultWhenErrorResponse() = runTest {
        coEvery {
            mockkRemoteRepository.getSearchResult(DEFAULT_SEARCH_KEYWORD)
        } returns NetworkResponse.Error(404, "Not found")
        val repository = SearchRepositoryImpl(mockkRemoteRepository)

        val dataFlow = repository.getSearchResult(DEFAULT_SEARCH_KEYWORD)
        val lastEmittedData = dataFlow.last()

        assertEquals(lastEmittedData::class.java, DataState.FetchError::class.java)
    }

    @Test
    fun getSearchResultWhenExceptionResponse() = runTest {
        coEvery {
            mockkRemoteRepository.getSearchResult(DEFAULT_SEARCH_KEYWORD)
        } returns NetworkResponse.Exception(NullPointerException())
        val repository = SearchRepositoryImpl(mockkRemoteRepository)

        val dataFlow = repository.getSearchResult(DEFAULT_SEARCH_KEYWORD)
        val lastEmittedData = dataFlow.last()

        assertEquals(lastEmittedData::class.java, DataState.FetchError::class.java)
    }
}

private const val DEFAULT_SEARCH_KEYWORD = "Ла-Ла Лэнд"