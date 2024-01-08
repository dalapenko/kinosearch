package tech.dalapenko.data.filmdetails.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.data.filmdetails.datasource.remote.RemoteDataSource
import tech.dalapenko.data.filmdetails.model.Film
class FilmDetailsRepositoryImplTest {

    private val mockkRemoteRepository = mockk<RemoteDataSource>()
    @Test
    fun getFilmDetailsWhenSuccessResponse() = runTest {
        val networkResponse = Film(
            id = 1, null, null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null,
            null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, emptyList(), emptyList(), null,
            null, null, null, null)

        coEvery {
            mockkRemoteRepository.getFilmDetails(DEFAULT_TEST_FILM_ID)
        } returns NetworkResponse.Success(networkResponse)
        val repository =
            FilmDetailsRepositoryImpl(mockkRemoteRepository)

        val dataFlow = repository.getFilmDetails(DEFAULT_TEST_FILM_ID)
        val lastEmittedData = dataFlow.last()

        assertEquals(lastEmittedData::class.java, DataState.Ready::class.java)
        assertEquals((lastEmittedData as? DataState.Ready)?.data, networkResponse)
    }

    @Test
    fun getFilmDetailsWhenErrorResponse() = runTest {
        coEvery {
            mockkRemoteRepository.getFilmDetails(DEFAULT_TEST_FILM_ID)
        } returns NetworkResponse.Error(404, "Not found")
        val repository =
            FilmDetailsRepositoryImpl(mockkRemoteRepository)

        val dataFlow = repository.getFilmDetails(DEFAULT_TEST_FILM_ID)
        val lastEmittedData = dataFlow.last()

        assertEquals(lastEmittedData::class.java, DataState.FetchError::class.java)
    }

    @Test
    fun getFilmDetailsWhenExceptionResponse() = runTest {
        coEvery {
            mockkRemoteRepository.getFilmDetails(DEFAULT_TEST_FILM_ID)
        } returns NetworkResponse.Exception(NullPointerException())
        val repository =
            FilmDetailsRepositoryImpl(mockkRemoteRepository)

        val dataFlow = repository.getFilmDetails(DEFAULT_TEST_FILM_ID)
        val lastEmittedData = dataFlow.last()

        assertEquals(lastEmittedData::class.java, DataState.FetchError::class.java)
    }
}

private const val DEFAULT_TEST_FILM_ID = 1