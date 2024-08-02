package tech.dalapenko.feature.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.feature.search.domain.CheckNetworkAvailableUseCase
import tech.dalapenko.feature.search.domain.GetSearchResultUseCase
import tech.dalapenko.feature.search.model.UiState
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class SearchViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    checkNetworkAvailableUseCase: CheckNetworkAvailableUseCase
) : ViewModel() {

    private var queryStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val innerMutableUiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(emptyFlow(), false)
    )
    val uiStateFlow = innerMutableUiStateFlow.asStateFlow()

    init {
        @OptIn(ExperimentalCoroutinesApi::class)
        queryStateFlow
            .debounce(500L)
            .filterNotNull()
            .distinctUntilChanged()
            .mapLatest(::getSearchResult)
            .onEach(::updatePagingDataUiState)
            .launchIn(viewModelScope)

        checkNetworkAvailableUseCase.invoke()
            .onEach(::updateNetworkUiState)
            .launchIn(viewModelScope)
    }

    fun updateQuery(query: CharSequence?) {
        queryStateFlow.tryEmit(query?.toString())
    }

    private fun getSearchResult(
        keyword: String
    ): Flow<PagingData<SearchResult>> {
        return getSearchResultUseCase.invoke(keyword)
            .cachedIn(viewModelScope)
    }

    private fun updatePagingDataUiState(
        pagingDataFlow: Flow<PagingData<SearchResult>>
    ) {
        innerMutableUiStateFlow.value.copy(
            pagingDataFlow = pagingDataFlow
        ).let(innerMutableUiStateFlow::tryEmit)
    }

    private fun updateNetworkUiState(isAvailable: Boolean) {
        innerMutableUiStateFlow.value.copy(
            isNetworkAvailable = isAvailable
        ).let(innerMutableUiStateFlow::tryEmit)
    }
}