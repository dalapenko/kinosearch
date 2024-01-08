package tech.dalapenko.data.releases.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.data.releases.datasource.local.LocalDataSource
import tech.dalapenko.data.releases.datasource.remote.RemoteDataSource
import tech.dalapenko.data.releases.model.Release

class ReleaseRepositoryImplTest {

    private val mockkRemoteRepository = mockk<RemoteDataSource>()
    private val mockkLocalRepository = mockk<LocalDataSource>()

    @Before
    fun setUp() {
        coEvery { mockkLocalRepository.insertReleaseList(any()) } returns Unit
    }

    @Test
    fun getReleasesWhenSuccessLocalAndNetworkResponse() = runTest {
        val localResponse = Release(
            id = 1, null, null, null, null,
            null, emptyList(), emptyList(), 0f, 0,
            1f, 1,
            null, null
        )

        val networkResponse = Release(
            id = 2, null, null, null, null,
            null, emptyList(), emptyList(), 0f, 0,
            1f, 1,
            null, null
        )

        coEvery {
            mockkRemoteRepository.getReleaseList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        } returns NetworkResponse.Success(listOf(networkResponse))
        coEvery {
            mockkLocalRepository.getReleaseList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        } returns listOf(localResponse)

        val repository = ReleaseRepositoryImpl(mockkRemoteRepository, mockkLocalRepository)

        val dataFlow = repository.getReleasesList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        val emittedFlow = dataFlow.drop(1)

        assertEquals(emittedFlow.first()::class.java, DataState.Cached::class.java)
        assertEquals((emittedFlow.first() as? DataState.Cached)?.data, listOf(localResponse))

        assertEquals(emittedFlow.last()::class.java, DataState.Current::class.java)
        assertEquals((emittedFlow.last() as? DataState.Current)?.data, listOf(networkResponse))

        coVerify {
            mockkLocalRepository.insertReleaseList(any())
        }
    }

    @Test
    fun getFilmDetailsWhenNetworkErrorResponseAndLocalSuccess() = runTest {
        val localResponse = Release(
            id = 1, null, null, null, null,
            null, emptyList(), emptyList(), 0f, 0,
            1f, 1,
            null, null
        )

        coEvery {
            mockkRemoteRepository.getReleaseList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        } returns NetworkResponse.Error(404, "Not Found")
        coEvery {
            mockkLocalRepository.getReleaseList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        } returns listOf(localResponse)

        val repository = ReleaseRepositoryImpl(mockkRemoteRepository, mockkLocalRepository)

        val dataFlow = repository.getReleasesList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        val emittedFlow = dataFlow.drop(1)

        assertEquals(emittedFlow.last()::class.java, DataState.Cached::class.java)
        assertEquals((emittedFlow.last() as? DataState.Cached)?.data, listOf(localResponse))

        coVerify(exactly = 0) {
            mockkLocalRepository.insertReleaseList(any())
        }
    }

    @Test
    fun getFilmDetailsWhenNetworkExceptionResponseAndLocalSuccess() = runTest {
        val localResponse = Release(
            id = 1, null, null, null, null,
            null, emptyList(), emptyList(), 0f, 0,
            1f, 1,
            null, null
        )

        coEvery {
            mockkRemoteRepository.getReleaseList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        } returns NetworkResponse.Exception(NullPointerException())
        coEvery {
            mockkLocalRepository.getReleaseList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        } returns listOf(localResponse)

        val repository = ReleaseRepositoryImpl(mockkRemoteRepository, mockkLocalRepository)

        val dataFlow = repository.getReleasesList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        val emittedFlow = dataFlow.drop(1)

        assertEquals(emittedFlow.last()::class.java, DataState.Cached::class.java)
        assertEquals((emittedFlow.last() as? DataState.Cached)?.data, listOf(localResponse))

        coVerify(exactly = 0) {
            mockkLocalRepository.insertReleaseList(any())
        }
    }

    @Test
    fun getFilmDetailsWhenNetworkErrorAndLocalEmptyResponse() = runTest {
        coEvery {
            mockkRemoteRepository.getReleaseList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        } returns NetworkResponse.Error(404, "Not Found")
        coEvery {
            mockkLocalRepository.getReleaseList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        } returns emptyList()

        val repository = ReleaseRepositoryImpl(mockkRemoteRepository, mockkLocalRepository)

        val dataFlow = repository.getReleasesList(DEFAULT_SEARCH_MONTH, DEFAULT_SEARCH_YEAR)
        val emittedFlow = dataFlow.drop(1)

        assertEquals(emittedFlow.last()::class.java, DataState.FetchError::class.java)

        coVerify(exactly = 0) {
            mockkLocalRepository.insertReleaseList(any())
        }
    }
}

private const val DEFAULT_SEARCH_MONTH = "JANUARY"
private const val DEFAULT_SEARCH_YEAR = 2024