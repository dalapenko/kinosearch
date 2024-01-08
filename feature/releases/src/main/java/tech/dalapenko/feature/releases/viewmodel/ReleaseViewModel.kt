package tech.dalapenko.feature.releases.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.feature.releases.domain.GetReleasesUseCase
import tech.dalapenko.feature.releases.model.UiState
import javax.inject.Inject

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val getReleasesUseCase: GetReleasesUseCase
) : ViewModel() {

    private val mutableContentStateFlow: MutableStateFlow<UiState<List<SectionRecyclerAdapter.Item>>> =
        MutableStateFlow(UiState.Loading)
    val contentStateFlow = mutableContentStateFlow.asStateFlow()


    fun fetchReleasesList(month: String, year: Int) {
        viewModelScope.launch {
            getReleasesUseCase(month, year)
                .collect(mutableContentStateFlow::emit)
        }
    }
}
