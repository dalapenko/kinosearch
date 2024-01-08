package tech.dalapenko.feature.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.data.search.repository.DataState
import tech.dalapenko.data.search.repository.SearchRepository
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private var queryStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val mutableViewStateFlow: MutableStateFlow<UiState<List<SearchResult>>> =
        MutableStateFlow(UiState.Ready(emptyList()))
    val viewStateFlow = mutableViewStateFlow.asStateFlow()

    init {
        queryStateFlow
            .debounce(500L)
            .filterNotNull()
            .filter {
                if (it.isBlank() || it.isEmpty()) {
                    mutableViewStateFlow.emit(UiState.Ready(emptyList()))
                    false
                } else {
                    true
                }
            }
            .distinctUntilChanged()
            .onEach {
                mutableViewStateFlow.emit(UiState.Loading)
                searchRepository.getSearchResult(it)
                    .collect { data ->
                        val uiState = when(data) {
                            is DataState.Current -> UiState.Ready(data.data)
                            is DataState.Loading -> UiState.Loading
                            is DataState.FetchError -> UiState.Error
                        }
                        mutableViewStateFlow.emit(uiState)
                    }
            }
            .launchIn(viewModelScope)
    }


    fun updateQuery(query: CharSequence?) {
        queryStateFlow.tryEmit(query.toString())
    }
}