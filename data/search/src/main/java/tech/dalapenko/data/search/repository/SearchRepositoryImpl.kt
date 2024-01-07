package tech.dalapenko.data.search.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.dalapenko.data.search.datasource.remote.RemoteDataSource
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.network.adapter.NetworkResponse

internal class SearchRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    override suspend fun getSearchResult(
        keyword: String
    ): Flow<DataState<List<SearchResult>>> = flow {

        emit(DataState.Loading)

        when (val searchResultResponse = remoteDataSource.getSearchResult(keyword)) {
            is NetworkResponse.Success -> emit(DataState.Current(searchResultResponse.data))
            else -> emit(DataState.FetchError)
        }
    }
}