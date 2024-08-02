package tech.dalapenko.feature.search.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import tech.dalapenko.data.search.model.SearchResult

data class UiState(
    val pagingDataFlow: Flow<PagingData<SearchResult>>,
    var isNetworkAvailable: Boolean
)