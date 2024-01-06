package tech.dalapenko.search.model.repository

import android.content.res.Resources.NotFoundException
import tech.dalapenko.network.api.KinoApi
import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.network.adapter.mapOnSuccess
import tech.dalapenko.network.adapter.onSuccess
import tech.dalapenko.search.model.entity.SearchResult
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val kinoApi: KinoApi
) : SearchRepository {

//    override suspend fun getSearchResult(
//        keyword: String
//    ): NetworkResponse<List<SearchResult>> {
//        var page = 1
//        val contentList = mutableListOf<SearchResult>()
//
//        while (true) {
//            val response = kinoApi.getSearchResult(
//                keyword = keyword, page = page
//            )
//
//            var isLast = response is NetworkResponse.Success
//            val responseItems = response.mapOnSuccess {
//                val responseItem = it.films ?: emptyList()
//                responseItem.map(SearchResult::mapFromDto)
//            }
//
//            if (responseItems is NetworkResponse.Success) {
//                contentList.addAll(responseItems.data)
//                response.onSuccess {
//                    isLast = it.pagesCount == page
//                }
//            } else {
//                return if (contentList.isEmpty()) {
//                    responseItems
//                } else {
//                    NetworkResponse.Exception(NotFoundException("Particular data fetch error"))
//                }
//            }
//
//            if (isLast) break
//            page++
//        }
//
//        return NetworkResponse.Success(contentList)
//    }

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