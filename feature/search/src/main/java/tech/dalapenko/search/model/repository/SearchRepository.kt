package tech.dalapenko.search.model.repository

import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.search.model.entity.SearchResult

interface SearchRepository {

    suspend fun getSearchResult(keyword: String): NetworkResponse<List<SearchResult>>
}