package tech.dalapenko.search.model.repository

import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.network.adapter.mapOnSuccess
import tech.dalapenko.network.api.KinoApi
import tech.dalapenko.search.model.entity.SearchResult
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val kinoApi: KinoApi
) : SearchRepository {

    override suspend fun getSearchResult(
        keyword: String
    ): NetworkResponse<List<SearchResult>> {
        val response = kinoApi.getSearchResult(
            keyword = keyword, page = 1
        )

        return response.mapOnSuccess {
            val responseItem = it.films ?: emptyList()
            responseItem.map(SearchResult::mapFromDto)
        }
    }
}