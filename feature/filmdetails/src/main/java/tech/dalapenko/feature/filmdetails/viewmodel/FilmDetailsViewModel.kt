package tech.dalapenko.feature.filmdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.dalapenko.data.filmdetails.model.Film
import tech.dalapenko.data.filmdetails.repository.DataState
import tech.dalapenko.data.filmdetails.repository.FilmDetailsRepository
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
    private val filmDetailsRepository: FilmDetailsRepository
) : ViewModel() {

    private val mutableContentUiStateFlow: MutableStateFlow<UiState<Film>> =
        MutableStateFlow(UiState.Loading)
    val contentStateFlow = mutableContentUiStateFlow.asStateFlow()

    fun fetchFilmData(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mutableContentUiStateFlow.emit(UiState.Loading)
                filmDetailsRepository.getFilmDetails(id)
                    .collect { data ->
                        val uiState = when(data) {
                            is DataState.Current -> UiState.Success(data.data)
                            is DataState.Loading -> UiState.Loading
                            is DataState.FetchError -> UiState.Error
                        }

                        mutableContentUiStateFlow.emit(uiState)
                    }
            }
        }
    }
}
