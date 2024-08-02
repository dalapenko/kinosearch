package tech.dalapenko.data.search.repository

import androidx.paging.Pager
import tech.dalapenko.data.search.model.SearchResult

interface SearchRepository {

    fun getSearchResultPager(
        keyword: String,
        initialLoadSize: Int
    ): Pager<Int, SearchResult>
}
