package tech.dalapenko.feature.search.domain

import kotlinx.coroutines.flow.flow
import tech.dalapenko.data.search.repository.DataState
import tech.dalapenko.data.search.repository.SearchRepository
import tech.dalapenko.feature.search.model.UiState
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(
        keyword: String
    ) = flow {
        emit(UiState.Loading)
        searchRepository.getSearchResult(keyword)
            .collect { data ->
                val uiState = when(data) {
                    is DataState.Current -> UiState.Ready(data.data)
                    is DataState.Loading -> UiState.Loading
                    is DataState.FetchError -> UiState.Error
                }
                emit(uiState)
            }
    }
}