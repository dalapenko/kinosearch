package tech.dalapenko.feature.releases.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

data class UiState(
    val pagingDataFlow: Flow<PagingData<ReleasePagingItem>>,
    var isNetworkAvailable: Boolean
)