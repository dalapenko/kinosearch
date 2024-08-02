package tech.dalapenko.data.search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.data.search.datasource.remote.PagingDataSource

internal class SearchRepositoryImpl(
    private val pagingDataSourceFactory: PagingDataSource.Factory
) : SearchRepository {

    override fun getSearchResultPager(
        keyword: String,
        initialLoadSize: Int
    ): Pager<Int, SearchResult> {
        return Pager(
            config = PagingConfig(pageSize = SEARCH_PAGE_SIZE, initialLoadSize = initialLoadSize),
            initialKey = 1,
            pagingSourceFactory = { pagingDataSourceFactory.create(keyword) }
        )
    }
}

private const val SEARCH_PAGE_SIZE = 20