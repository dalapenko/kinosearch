package tech.dalapenko.data.search.repository

import kotlinx.coroutines.flow.Flow
import tech.dalapenko.data.search.model.SearchResult

interface SearchRepository {

    suspend fun getSearchResult(keyword: String): Flow<DataState<List<SearchResult>>>
}