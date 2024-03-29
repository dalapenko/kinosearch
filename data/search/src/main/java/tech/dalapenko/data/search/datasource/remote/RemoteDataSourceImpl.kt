package tech.dalapenko.data.search.datasource.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.core.network.adapter.mapOnSuccess
import tech.dalapenko.core.network.api.KinoApi
import tech.dalapenko.core.network.dto.FilmSearchItemDto
import tech.dalapenko.core.network.mapper.DtoMapper
import javax.inject.Inject

internal class RemoteDataSourceImpl @Inject constructor(
    private val kinoApi: KinoApi,
    private val dtoMapper: DtoMapper<FilmSearchItemDto, SearchResult>
) : RemoteDataSource {

    override suspend fun getSearchResult(
        keyword: String
    ): NetworkResponse<List<SearchResult>> = withContext(Dispatchers.IO) {
        val response = kinoApi.getSearchResult(
            keyword = keyword, page = 1
        )

        return@withContext response.mapOnSuccess {
            val responseItem = it.films ?: emptyList()
            responseItem.map(dtoMapper::mapToModel)
        }
    }
}