package tech.dalapenko.data.filmdetails.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.dalapenko.data.filmdetails.datasource.remote.RemoteDataSource
import tech.dalapenko.data.filmdetails.model.Film
import tech.dalapenko.core.network.adapter.NetworkResponse

internal class FilmDetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : FilmDetailsRepository {

    override suspend fun getFilmDetails(id: Int): Flow<DataState<Film>> = flow {

        emit(DataState.Loading)

        when (val filmDetailsResponse = remoteDataSource.getFilmDetails(id)) {
            is NetworkResponse.Success -> emit(DataState.Current(filmDetailsResponse.data))
            else -> emit(DataState.FetchError)
        }
    }
}