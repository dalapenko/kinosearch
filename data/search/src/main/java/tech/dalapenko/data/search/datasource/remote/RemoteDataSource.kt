package tech.dalapenko.data.search.datasource.remote

import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.core.network.adapter.NetworkResponse

interface RemoteDataSource {

    suspend fun getSearchResult(keyword: String): NetworkResponse<List<SearchResult>>
}