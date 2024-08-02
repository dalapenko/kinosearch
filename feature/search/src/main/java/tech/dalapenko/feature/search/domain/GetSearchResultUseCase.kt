package tech.dalapenko.feature.search.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.data.search.repository.SearchRepository
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val searchPagingRepository: SearchRepository
) {

    operator fun invoke(
        keyword: String
    ): Flow<PagingData<SearchResult>> {
        return if (keyword.isBlank()) {
            flowOf(PagingData.empty())
        } else {
            searchPagingRepository.getSearchResultPager(keyword, INITIAL_LOAD_SIZE).flow
        }
    }
}

private const val INITIAL_LOAD_SIZE = 1
