package tech.dalapenko.feature.filmdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.dalapenko.data.filmdetails.model.Film
import tech.dalapenko.feature.filmdetails.domain.GetFilmDetailsUseCase
import tech.dalapenko.feature.filmdetails.model.UiState
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase
) : ViewModel() {

    private val mutableContentUiStateFlow: MutableStateFlow<UiState<Film>> =
        MutableStateFlow(UiState.Loading)
    val contentStateFlow = mutableContentUiStateFlow.asStateFlow()

    fun fetchFilmData(id: Int) {
        viewModelScope.launch {
            getFilmDetailsUseCase(id)
                .collect(mutableContentUiStateFlow::emit)
        }
    }
}
