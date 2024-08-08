package tech.dalapenko.feature.releases.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.dalapenko.feature.releases.domain.CheckNetworkAvailableUseCase
import tech.dalapenko.feature.releases.domain.GetReleasesUseCase
import tech.dalapenko.feature.releases.model.ReleasePagingItem
import tech.dalapenko.feature.releases.model.UiState
import javax.inject.Inject

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val getReleasesUseCase: GetReleasesUseCase,
    checkNetworkAvailableUseCase: CheckNetworkAvailableUseCase
) : ViewModel() {

    private val innerMutableUiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(emptyFlow(), false)
    )
    val uiStateFlow = innerMutableUiStateFlow.asStateFlow()

    init {
        checkNetworkAvailableUseCase.invoke()
            .onEach(::updateNetworkUiState)
            .launchIn(viewModelScope)
    }

    fun fetchReleasesList(month: String, year: Int) {
        updatePagingDataUiState(getReleasesUseCase(month, year))
    }

    private fun updatePagingDataUiState(
        pagingDataFlow: Flow<PagingData<ReleasePagingItem>>
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
