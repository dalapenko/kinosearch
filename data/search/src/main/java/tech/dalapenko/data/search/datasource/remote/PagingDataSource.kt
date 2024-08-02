package tech.dalapenko.data.search.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import tech.dalapenko.core.network.adapter.NetworkResponse
import tech.dalapenko.core.network.api.KinoApi
import tech.dalapenko.core.network.dto.FilmSearchItemDto
import tech.dalapenko.core.network.mapper.DtoMapper
import tech.dalapenko.data.search.model.SearchResult

class PagingDataSource @AssistedInject constructor(
    private val kinoApi: KinoApi,
    private val dtoMapper: DtoMapper<FilmSearchItemDto, SearchResult>,
    @Assisted private val keyword: String
) : PagingSource<Int, SearchResult>() {

    override fun getRefreshKey(state: PagingState<Int, SearchResult>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResult> {
        if (keyword.isEmpty()) {
            return LoadResult.Page(emptyList(), null, null)
        }

        val page = params.key ?: 1
        val pageSize = params.loadSize.coerceAtMost(KinoApi.MAX_PAGE_SIZE)

        return when(val response = kinoApi.getSearchResult(keyword, page)) {
            is NetworkResponse.Error -> {
                LoadResult.Error(response.throwable)
            }
            is NetworkResponse.Exception -> {
                LoadResult.Error(response.throwable)
            }
            is NetworkResponse.Success -> {
                val data = response.data.films?.map(dtoMapper::mapToModel) ?: emptyList()
                val isLastPage = page == response.data.pagesCount || data.size < pageSize
                val nextKey = if (isLastPage) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(data, prevKey, nextKey)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(keyword: String): PagingDataSource
    }
}
