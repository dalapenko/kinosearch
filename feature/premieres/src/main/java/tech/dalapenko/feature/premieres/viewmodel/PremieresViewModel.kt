package tech.dalapenko.feature.premieres.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.dalapenko.feature.premieres.domain.GetPremieresUseCase
import tech.dalapenko.feature.premieres.model.PremiereRecyclerItem
import tech.dalapenko.feature.premieres.model.UiState
import javax.inject.Inject

@HiltViewModel
class PremieresViewModel @Inject constructor(
    private val getReleasesUseCase: GetPremieresUseCase
) : ViewModel() {

    private val mutableContentStateFlow: MutableStateFlow<UiState<List<PremiereRecyclerItem>>> =
        MutableStateFlow(UiState.Loading)
    val contentStateFlow = mutableContentStateFlow.asStateFlow()

    fun fetchContentList(month: String, year: Int) {
        viewModelScope.launch {
            getReleasesUseCase(month, year)
                .collect(mutableContentStateFlow::emit)
        }

    }
}
