package tech.dalapenko.search.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.dalapenko.network.adapter.NetworkResponse
import tech.dalapenko.search.model.State
import tech.dalapenko.search.model.entity.SearchResult
import tech.dalapenko.search.model.repository.SearchRepository
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private var queryStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val mutableViewStateFlow: MutableStateFlow<State<List<SearchResult>>> =
        MutableStateFlow(State.Success(emptyList()))
    val viewStateFlow = mutableViewStateFlow.asStateFlow()

    init {
        queryStateFlow
            .debounce(500L)
            .filterNotNull()
            .filter {
                if (it.isBlank() || it.isEmpty()) {
                    mutableViewStateFlow.emit(State.Success(emptyList()))
                    false
                } else {
                    true
                }
            }
            .distinctUntilChanged()
            .onEach {
                mutableViewStateFlow.emit(State.Loading)
                val state = when(val response = searchByQuery(it)) {
                    is NetworkResponse.Success -> State.Success(response.data)
                    else -> State.Error
                }
                mutableViewStateFlow.emit(state)
            }
            .launchIn(viewModelScope)
    }


    fun updateQuery(query: CharSequence?) {
        queryStateFlow.tryEmit(query.toString())
    }

    private suspend fun searchByQuery(query: String): NetworkResponse<List<SearchResult>> {
//        delay(2000L)
//        return NetworkResponse.Success(emptyList())
        return searchRepository.getSearchResult(query)
    }

}